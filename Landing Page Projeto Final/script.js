/* PrintGest: simulador de orçamento e interações da página.
   As regras de cálculo abaixo repetem as do PedidoService (Java). */

// ================= DADOS DO SISTEMA (iguais ao PedidoService) =================
const FOLHA_ALTURA = 46.5; // cm
const FOLHA_LARGURA = 31.5; // cm
const TAXA_FIXA = 10.0;

const PAPEIS = [
    'Couchê Brilho ADC5420',
    'Couchê Brilho ADC6000',
    'BOPP Branco',
    'BOPP Transparente',
    'BOPP Metalizado',
    'Vinil Fasson Branco',
    'Vinil Fasson Transparente',
];

const TABELA_PRECOS = [
    [6.4, 6.1, 5.7, 5.0, 4.3, 3.8, 3.5, 3.2],
    [6.8, 6.5, 6.1, 5.3, 4.6, 4.0, 3.7, 3.4],
    [9.5, 9.1, 8.5, 7.0, 6.0, 5.4, 4.8, 4.3],
    [10.8, 10.3, 9.7, 7.9, 6.9, 6.1, 5.4, 4.9],
    [11.2, 10.7, 10.0, 8.2, 7.1, 6.3, 5.6, 5.1],
    [15.6, 14.9, 13.9, 11.4, 9.9, 8.8, 7.8, 7.1],
    [15.6, 14.9, 13.9, 11.4, 9.9, 8.8, 7.8, 7.1],
];

const LIMITES_FAIXA = [20, 50, 100, 200, 300, 400, 500];
const NOMES_FAIXA = ['até 20', '21 a 50', '51 a 100', '101 a 200', '201 a 300', '301 a 400', '401 a 500', 'acima de 500'];

const reduzirMovimento = window.matchMedia('(prefers-reduced-motion: reduce)').matches;

// ================= FORMATAÇÃO =================
const brl = new Intl.NumberFormat('pt-BR', { style: 'currency', currency: 'BRL' });
const num = new Intl.NumberFormat('pt-BR', { maximumFractionDigits: 2 });
const pct = new Intl.NumberFormat('pt-BR', { maximumFractionDigits: 1 });

// ================= REGRAS DE CÁLCULO =================
function calcularAproveitamento(altura, largura) {
    const colunas1 = Math.floor(FOLHA_ALTURA / altura);
    const linhas1 = Math.floor(FOLHA_LARGURA / largura);
    const colunas2 = Math.floor(FOLHA_ALTURA / largura);
    const linhas2 = Math.floor(FOLHA_LARGURA / altura);

    const op1 = colunas1 * linhas1;
    const op2 = colunas2 * linhas2;

    return op1 >= op2
        ? { total: op1, colunas: colunas1, linhas: linhas1, celulaW: altura, celulaH: largura }
        : { total: op2, colunas: colunas2, linhas: linhas2, celulaW: largura, celulaH: altura };
}

function indiceFaixa(folhas) {
    const i = LIMITES_FAIXA.findIndex((limite) => folhas <= limite);
    return i === -1 ? LIMITES_FAIXA.length : i;
}

// Mesmas mensagens e regras de validação do PedidoService
function lerNumero(texto, inteiro) {
    const t = texto.trim().replace(',', '.');
    const padrao = inteiro ? /^\d+$/ : /^\d+(\.\d+)?$/;
    return padrao.test(t) ? Number(t) : null;
}

// ================= SIMULADOR =================
const el = (id) => document.getElementById(id);
const selPapel = el('papel');
const inAltura = el('altura');
const inLargura = el('largura');
const inQtd = el('qtd');
const usada = el('used');
const celula = el('cell-rect');
const padraoCelulas = el('cells');
const nota = el('sheet-note');
const erros = el('sim-erros');

PAPEIS.forEach((nome, i) => {
    const op = document.createElement('option');
    op.value = String(i);
    op.textContent = nome;
    if (nome === 'BOPP Branco') op.selected = true;
    selPapel.appendChild(op);
});

// Tabela de preços
const tabela = el('tabela-precos');
tabela.innerHTML =
    '<thead><tr><th scope="col" style="text-align:left">Papel</th>' +
    NOMES_FAIXA.map((n) => `<th scope="col">${n}</th>`).join('') +
    '</tr></thead><tbody>' +
    PAPEIS.map(
        (nome, i) =>
            `<tr><th scope="row">${nome}</th>` +
            TABELA_PRECOS[i].map((p) => `<td>${p.toFixed(2).replace('.', ',')}</td>`).join('') +
            '</tr>'
    ).join('') +
    '</tbody>';

function limparDestaque() {
    tabela.querySelectorAll('.on, .hit').forEach((n) => n.classList.remove('on', 'hit'));
}

function destacarTabela(papel, faixa) {
    limparDestaque();
    const linha = tabela.tBodies[0].rows[papel];
    linha.classList.add('on');
    linha.cells[faixa + 1].classList.add('hit');
    tabela.tHead.rows[0].cells[faixa + 1].classList.add('on');
}

function mostrarSaida({ rpf = '—', folhas = '—', uso = '—', valor = '—', faixa = '—' }) {
    el('out-rpf').textContent = rpf;
    el('out-folhas').textContent = folhas;
    el('out-uso').textContent = uso;
    el('out-valor').textContent = valor;
    el('p-rpf').textContent = rpf === '—' ? '—' : `${rpf} por folha`;
    el('p-folhas').textContent = folhas === '—' ? '—' : `${folhas} folhas`;
    el('p-faixa').textContent = faixa;
    el('p-valor').textContent = valor;
}

function esvaziarFolha() {
    usada.setAttribute('width', 0);
    usada.setAttribute('height', 0);
}

function desenharFolha(a) {
    const w = a.celulaW * 10;
    const h = a.celulaH * 10;
    padraoCelulas.setAttribute('width', w);
    padraoCelulas.setAttribute('height', h);
    celula.setAttribute('width', w);
    celula.setAttribute('height', h);
    celula.setAttribute('stroke-width', Math.min(0.8, Math.min(w, h) * 0.1).toFixed(2));
    usada.setAttribute('width', a.colunas * w);
    usada.setAttribute('height', a.linhas * h);

    if (!reduzirMovimento) {
        usada.classList.remove('flash');
        void usada.getBoundingClientRect();
        usada.classList.add('flash');
    }
}

function atualizar() {
    const altura = lerNumero(inAltura.value, false);
    const largura = lerNumero(inLargura.value, false);
    const qtd = lerNumero(inQtd.value, true);
    const papel = Number(selPapel.value);

    const lista = [];
    if (!altura) lista.push('- Altura do rótulo é obrigatória e deve ser um número maior que zero');
    if (!largura) lista.push('- Largura do rótulo é obrigatória e deve ser um número maior que zero');
    if (!qtd) lista.push('- Quantidade de rótulos é obrigatória e deve ser um número inteiro maior que zero');

    inAltura.setAttribute('aria-invalid', String(!altura));
    inLargura.setAttribute('aria-invalid', String(!largura));
    inQtd.setAttribute('aria-invalid', String(!qtd));

    el('p-medidas').textContent =
        altura && largura ? `${num.format(altura)} × ${num.format(largura)} cm` : '—';

    nota.hidden = true;
    erros.hidden = lista.length === 0;
    erros.textContent = lista.length ? 'Ops! Erros encontrados:\n' + lista.join('\n') : '';

    if (lista.length) {
        esvaziarFolha();
        limparDestaque();
        mostrarSaida({});
        return;
    }

    const a = calcularAproveitamento(altura, largura);
    if (a.total <= 0) {
        esvaziarFolha();
        limparDestaque();
        mostrarSaida({});
        nota.textContent = 'O rótulo não cabe na folha. Informe dimensões menores.';
        nota.hidden = false;
        return;
    }

    const folhas = Math.ceil(qtd / a.total);
    const faixa = indiceFaixa(folhas);
    const precoFolha = TABELA_PRECOS[papel][faixa];
    const valor = precoFolha * folhas + TAXA_FIXA;
    const uso = ((a.total * altura * largura) / (FOLHA_ALTURA * FOLHA_LARGURA)) * 100;

    desenharFolha(a);
    destacarTabela(papel, faixa);
    mostrarSaida({
        rpf: num.format(a.total),
        folhas: num.format(folhas),
        uso: `${pct.format(uso)}%`,
        valor: brl.format(valor),
        faixa: `${brl.format(precoFolha)} por folha`,
    });
}

[selPapel, inAltura, inLargura, inQtd].forEach((c) => c.addEventListener('input', atualizar));
atualizar();

// ================= NÚMEROS DO HERO =================
document.querySelectorAll('[data-count]').forEach((dd, i) => {
    const alvo = Number(dd.dataset.count);
    if (reduzirMovimento) return;
    dd.textContent = '0';
    const inicio = performance.now() + 520 + i * 90;
    const duracao = 900;
    const passo = (agora) => {
        const t = Math.min(Math.max((agora - inicio) / duracao, 0), 1);
        dd.textContent = String(Math.round(alvo * (1 - Math.pow(1 - t, 3))));
        if (t < 1) requestAnimationFrame(passo);
    };
    requestAnimationFrame(passo);
});

// ================= CABEÇALHO =================
const topbar = document.getElementById('topbar');
const aoRolar = () => topbar.classList.toggle('scrolled', window.scrollY > 8);
window.addEventListener('scroll', aoRolar, { passive: true });
aoRolar();

// ================= REVELAR AO ROLAR =================
const revelaveis = document.querySelectorAll('[data-reveal]');
if ('IntersectionObserver' in window) {
    const obs = new IntersectionObserver(
        (entradas) => {
            entradas.forEach((e) => {
                if (e.isIntersecting) {
                    e.target.classList.add('is-in');
                    obs.unobserve(e.target);
                }
            });
        },
        { threshold: 0.12, rootMargin: '0px 0px -6% 0px' }
    );
    revelaveis.forEach((n) => obs.observe(n));
} else {
    revelaveis.forEach((n) => n.classList.add('is-in'));
}

// ================= ABAS DA ARQUITETURA =================
document.querySelectorAll('[data-tabs]').forEach((grupo) => {
    const abas = Array.from(grupo.querySelectorAll('[role="tab"]'));

    const ativar = (aba, foco) => {
        abas.forEach((a) => {
            const on = a === aba;
            a.setAttribute('aria-selected', String(on));
            a.tabIndex = on ? 0 : -1;
            document.getElementById(a.getAttribute('aria-controls')).hidden = !on;
        });
        if (foco) aba.focus();
    };

    abas.forEach((aba, i) => {
        aba.addEventListener('click', () => ativar(aba, false));
        aba.addEventListener('keydown', (e) => {
            const mapa = { ArrowDown: 1, ArrowRight: 1, ArrowUp: -1, ArrowLeft: -1 };
            if (e.key in mapa) {
                e.preventDefault();
                ativar(abas[(i + mapa[e.key] + abas.length) % abas.length], true);
            } else if (e.key === 'Home') {
                e.preventDefault();
                ativar(abas[0], true);
            } else if (e.key === 'End') {
                e.preventDefault();
                ativar(abas[abas.length - 1], true);
            }
        });
    });
});

// ================= COPIAR COMANDOS =================
document.querySelectorAll('.copy').forEach((botao) => {
    botao.addEventListener('click', async () => {
        const texto = botao.dataset.copy;
        try {
            await navigator.clipboard.writeText(texto);
        } catch (e) {
            const area = document.createElement('textarea');
            area.value = texto;
            area.style.position = 'fixed';
            area.style.opacity = '0';
            document.body.appendChild(area);
            area.select();
            document.execCommand('copy');
            area.remove();
        }
        botao.textContent = 'Copiado';
        botao.classList.add('done');
        setTimeout(() => {
            botao.textContent = 'Copiar';
            botao.classList.remove('done');
        }, 1600);
    });
});
