/* Service worker do PrintGest: deixa a página funcionar offline.
   - Arquivos da página: respondidos do cache e atualizados em segundo plano.
   - Navegação: tenta a rede e, sem internet, abre a versão guardada.
   - Fontes do Google: guardadas na primeira visita. */

const VERSAO = 'printgest-v1';
const ARQUIVOS = [
    './',
    './index.html',
    './style.css',
    './script.js',
    './manifest.webmanifest',
    './demonstracao.gif',
    './icons/icon-192.png',
    './icons/icon-512.png',
    './icons/maskable-192.png',
    './icons/maskable-512.png',
    './icons/apple-touch-icon.png',
];

self.addEventListener('install', (evento) => {
    evento.waitUntil(caches.open(VERSAO).then((cache) => cache.addAll(ARQUIVOS)).then(() => self.skipWaiting()));
});

self.addEventListener('activate', (evento) => {
    evento.waitUntil(
        caches
            .keys()
            .then((chaves) => Promise.all(chaves.filter((c) => c !== VERSAO).map((c) => caches.delete(c))))
            .then(() => self.clients.claim())
    );
});

self.addEventListener('fetch', (evento) => {
    const req = evento.request;
    if (req.method !== 'GET') return;
    const url = new URL(req.url);

    // Navegação: rede primeiro, cache como reserva
    if (req.mode === 'navigate') {
        evento.respondWith(
            fetch(req)
                .then((resp) => {
                    const copia = resp.clone();
                    caches.open(VERSAO).then((c) => c.put('./index.html', copia));
                    return resp;
                })
                .catch(() => caches.match('./index.html'))
        );
        return;
    }

    // Mesmo site e fontes do Google: cache primeiro, atualizando em segundo plano
    const mesmoSite = url.origin === self.location.origin;
    const fontes = url.hostname === 'fonts.googleapis.com' || url.hostname === 'fonts.gstatic.com';
    if (!mesmoSite && !fontes) return;

    evento.respondWith(
        caches.match(req).then((guardado) => {
            const rede = fetch(req)
                .then((resp) => {
                    if (resp && (resp.ok || resp.type === 'opaque')) {
                        const copia = resp.clone();
                        caches.open(VERSAO).then((c) => c.put(req, copia));
                    }
                    return resp;
                })
                .catch(() => guardado);
            return guardado || rede;
        })
    );
});
