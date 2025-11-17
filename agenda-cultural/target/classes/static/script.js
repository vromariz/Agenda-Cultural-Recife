const API_URL = 'http://localhost:8080';
let currentUser = null;
let eventosFavoritos = JSON.parse(localStorage.getItem('favoritos') || '[]');
let eventoParaExcluir = null;
let eventoParaEditar = null;

// Inicialização
document.addEventListener('DOMContentLoaded', () => {
    verificarLogin();
    setupForms();
    
    // Mostrar seção inicial baseado no login
    if (currentUser) {
        carregarEventos();
        showSection('eventos');
    } else {
        showSection('login');
    }
});

// Verificar se usuário está logado
function verificarLogin() {
    const user = localStorage.getItem('currentUser');
    if (user) {
        currentUser = JSON.parse(user);
        atualizarNavegacao();
    } else {
        atualizarNavegacao();
    }
}

// Atualizar navegação baseado no login
function atualizarNavegacao() {
    const navLoggedOut = document.getElementById('nav-logged-out');
    const navLoggedIn = document.getElementById('nav-logged-in');
    const navCadastrarEvento = document.getElementById('nav-cadastrar-evento');
    
    if (currentUser) {
        navLoggedOut.style.display = 'none';
        navLoggedIn.style.display = 'flex';
        
        // Mostrar "Cadastrar Evento" apenas para admin
        if (currentUser.role === 'ADMIN') {
            navCadastrarEvento.style.display = 'block';
        } else {
            navCadastrarEvento.style.display = 'none';
        }
    } else {
        navLoggedOut.style.display = 'flex';
        navLoggedIn.style.display = 'none';
    }
}

// Setup dos formulários
function setupForms() {
    // Formulário de login
    document.getElementById('login-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        await fazerLogin();
    });

    // Formulário de cadastro
    document.getElementById('register-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        await cadastrarUsuario();
    });

    // Formulário de evento
    document.getElementById('event-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        await cadastrarEvento();
    });

    // Formulário de edição
    document.getElementById('edit-event-form').addEventListener('submit', async (e) => {
        e.preventDefault();
        await atualizarEvento();
    });
}

// Mostrar seção
function showSection(sectionId) {
    // Verificar permissões
    if (sectionId === 'cadastrar-evento' && (!currentUser || currentUser.role !== 'ADMIN')) {
        alert('Apenas administradores podem cadastrar eventos!');
        return;
    }
    
    if ((sectionId === 'favoritos' || sectionId === 'acessibilidade') && !currentUser) {
        alert('Você precisa estar logado para acessar esta funcionalidade!');
        showSection('login');
        return;
    }
    
    document.querySelectorAll('.content-section').forEach(section => {
        section.style.display = 'none';
    });
    document.getElementById(sectionId).style.display = 'block';
    
    if (sectionId === 'eventos') {
        carregarEventos();
    } else if (sectionId === 'favoritos') {
        carregarFavoritos();
    }
}

// Fazer Login
async function fazerLogin() {
    const email = document.getElementById('login-email').value;
    const password = document.getElementById('login-password').value;

    try {
        const response = await fetch(`${API_URL}/users/login`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ email, password })
        });

        if (response.ok) {
            const user = await response.json();
            localStorage.setItem('currentUser', JSON.stringify(user));
            currentUser = user;
            atualizarNavegacao();
            alert('Login realizado com sucesso!');
            document.getElementById('login-form').reset();
            showSection('eventos');
        } else {
            const error = await response.text();
            alert('Erro ao fazer login: ' + error);
        }
    } catch (error) {
        alert('Erro ao fazer login: ' + error.message);
    }
}

// Logout
function logout() {
    if (confirm('Deseja realmente sair?')) {
        localStorage.removeItem('currentUser');
        currentUser = null;
        atualizarNavegacao();
        showSection('login');
        alert('Logout realizado com sucesso!');
    }
}

// Cadastrar Usuário
async function cadastrarUsuario() {
    const name = document.getElementById('register-name').value;
    const email = document.getElementById('register-email').value;
    const password = document.getElementById('register-password').value;
    const confirmPassword = document.getElementById('register-confirm-password').value;
    const role = document.getElementById('register-role').value;

    if (password !== confirmPassword) {
        alert('Senhas não conferem!');
        return;
    }

    try {
        const response = await fetch(`${API_URL}/users/register`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, email, password, confirmPassword, role })
        });

        if (response.ok) {
            const user = await response.json();
            localStorage.setItem('currentUser', JSON.stringify(user));
            currentUser = user;
            atualizarNavegacao();
            alert('Usuário cadastrado com sucesso! Você já está logado.');
            document.getElementById('register-form').reset();
            showSection('eventos');
        } else {
            const error = await response.text();
            alert('Erro ao cadastrar: ' + error);
        }
    } catch (error) {
        alert('Erro ao cadastrar usuário: ' + error.message);
    }
}

// Cadastrar Evento
async function cadastrarEvento() {
    if (!currentUser) {
        alert('Você precisa estar logado para cadastrar eventos!');
        showSection('login');
        return;
    }
    
    if (currentUser.role !== 'ADMIN') {
        alert('Apenas administradores podem cadastrar eventos!');
        return;
    }

    const evento = {
        nome: document.getElementById('event-nome').value,
        descricao: document.getElementById('event-descricao').value,
        local: document.getElementById('event-local').value,
        data: document.getElementById('event-data').value,
        categoria: document.getElementById('event-categoria').value,
        bairro: document.getElementById('event-bairro').value,
        preco: parseFloat(document.getElementById('event-preco').value) || null,
        usuarioId: currentUser.id
    };

    try {
        const response = await fetch(`${API_URL}/eventos`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(evento)
        });

        if (response.ok) {
            alert('Evento cadastrado com sucesso!');
            document.getElementById('event-form').reset();
            carregarEventos();
        } else {
            const error = await response.text();
            alert('Erro ao cadastrar evento: ' + error);
        }
    } catch (error) {
        alert('Erro ao cadastrar evento: ' + error.message);
    }
}

// Carregar Eventos
async function carregarEventos() {
    try {
        const response = await fetch(`${API_URL}/eventos`);
        if (response.ok) {
            const eventos = await response.json();
            exibirEventos(eventos);
        }
    } catch (error) {
        console.error('Erro ao carregar eventos:', error);
    }
}

// Exibir Eventos
function exibirEventos(eventos) {
    const container = document.getElementById('eventos-container');
    container.innerHTML = '';

    if (eventos.length === 0) {
        container.innerHTML = '<p>Nenhum evento encontrado.</p>';
        return;
    }

    eventos.forEach(evento => {
        const card = criarCardEvento(evento);
        container.appendChild(card);
    });
}

// Criar Card de Evento
function criarCardEvento(evento) {
    const card = document.createElement('div');
    card.className = 'evento-card';
    
    const dataFormatada = new Date(evento.data + 'T00:00:00').toLocaleDateString('pt-BR');
    const precoTexto = evento.preco ? `R$ ${evento.preco.toFixed(2)}` : 'Gratuito';
    
    card.innerHTML = `
        <img src="https://picsum.photos/300/200?random=${evento.id}" alt="${evento.nome}" class="evento-image" onerror="this.src='data:image/svg+xml,%3Csvg xmlns=\'http://www.w3.org/2000/svg\' width=\'300\' height=\'200\'%3E%3Crect fill=\'%23667eea\' width=\'300\' height=\'200\'/%3E%3Ctext x=\'50%25\' y=\'50%25\' text-anchor=\'middle\' dy=\'.3em\' fill=\'white\' font-size=\'20\'%3EEvento%3C/text%3E%3C/svg%3E'">
        <div class="evento-info">
            <div class="evento-titulo">${evento.nome}</div>
            <div class="evento-detalhes">Data: ${dataFormatada}</div>
            <div class="evento-detalhes">Bairro: ${evento.bairro || 'N/A'}</div>
            <div class="evento-detalhes">Preço: ${precoTexto}</div>
            <div class="evento-acoes">
                <a href="#" onclick="verDetalhes(${evento.id}); return false;">Detalhes</a>
                ${currentUser ? `
                    <a href="#" onclick="compartilharEvento(${evento.id}, '${evento.nome}'); return false;">Compartilhar</a>
                    <a href="#" onclick="toggleFavorito(${evento.id}); return false;">
                        ${eventosFavoritos.includes(evento.id) ? 'Desfavoritar' : 'Favoritar'}
                    </a>
                ` : ''}
                ${currentUser && currentUser.role === 'ADMIN' ? `
                    <a href="#" onclick="editarEvento(${evento.id}); return false;">Editar</a>
                    <a href="#" onclick="confirmarExclusaoDialog(${evento.id}, '${evento.nome}'); return false;">Excluir</a>
                ` : ''}
            </div>
        </div>
    `;
    
    return card;
}

// Ver Detalhes
async function verDetalhes(id) {
    try {
        const response = await fetch(`${API_URL}/eventos/${id}`);
        if (response.ok) {
            const evento = await response.json();
            mostrarDetalhes(evento);
        }
    } catch (error) {
        alert('Erro ao carregar detalhes: ' + error.message);
    }
}

function mostrarDetalhes(evento) {
    const dataFormatada = new Date(evento.data + 'T00:00:00').toLocaleDateString('pt-BR');
    const precoTexto = evento.preco ? `R$ ${evento.preco.toFixed(2)}` : 'Gratuito';
    
    document.getElementById('detalhes-content').innerHTML = `
        <div class="detalhe-item"><strong>Título:</strong> ${evento.nome}</div>
        <div class="detalhe-item"><strong>Descrição:</strong> ${evento.descricao}</div>
        <div class="detalhe-item"><strong>Data:</strong> ${dataFormatada}</div>
        <div class="detalhe-item"><strong>Local:</strong> ${evento.local}</div>
        <div class="detalhe-item"><strong>Bairro:</strong> ${evento.bairro || 'N/A'}</div>
        <div class="detalhe-item"><strong>Preço:</strong> ${precoTexto}</div>
    `;
    showSection('detalhes-evento');
}

// Editar Evento
async function editarEvento(id) {
    if (!currentUser || currentUser.role !== 'ADMIN') {
        alert('Apenas administradores podem editar eventos!');
        return;
    }
    
    try {
        const response = await fetch(`${API_URL}/eventos/${id}`);
        if (response.ok) {
            const evento = await response.json();
            eventoParaEditar = evento;
            preencherFormularioEdicao(evento);
            showSection('editar-evento');
        }
    } catch (error) {
        alert('Erro ao carregar evento: ' + error.message);
    }
}

function preencherFormularioEdicao(evento) {
    document.getElementById('edit-event-id').value = evento.id;
    document.getElementById('edit-event-nome').value = evento.nome;
    document.getElementById('edit-event-descricao').value = evento.descricao;
    document.getElementById('edit-event-local').value = evento.local;
    document.getElementById('edit-event-bairro').value = evento.bairro || '';
    document.getElementById('edit-event-preco').value = evento.preco || '';
    document.getElementById('edit-event-categoria').value = evento.categoria;
    document.getElementById('edit-event-data').value = evento.data;
}

// Atualizar Evento
async function atualizarEvento() {
    if (!currentUser || currentUser.role !== 'ADMIN') {
        alert('Apenas administradores podem editar eventos!');
        return;
    }

    const id = document.getElementById('edit-event-id').value;
    const evento = {
        nome: document.getElementById('edit-event-nome').value,
        descricao: document.getElementById('edit-event-descricao').value,
        local: document.getElementById('edit-event-local').value,
        data: document.getElementById('edit-event-data').value,
        categoria: document.getElementById('edit-event-categoria').value,
        bairro: document.getElementById('edit-event-bairro').value,
        preco: parseFloat(document.getElementById('edit-event-preco').value) || null,
        usuarioId: currentUser.id
    };

    try {
        const response = await fetch(`${API_URL}/eventos/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(evento)
        });

        if (response.ok) {
            alert('Evento atualizado com sucesso!');
            showSection('eventos');
            carregarEventos();
        } else {
            alert('Erro ao atualizar evento');
        }
    } catch (error) {
        alert('Erro ao atualizar evento: ' + error.message);
    }
}

// Confirmar Exclusão Dialog
function confirmarExclusaoDialog(id, nome) {
    if (!currentUser || currentUser.role !== 'ADMIN') {
        alert('Apenas administradores podem excluir eventos!');
        return;
    }
    
    eventoParaExcluir = id;
    document.getElementById('excluir-mensagem').textContent = 
        `Deseja realmente excluir o evento "${nome}"?`;
    showSection('excluir-evento');
}

// Confirmar Exclusão
async function confirmarExclusao() {
    if (!eventoParaExcluir) return;

    try {
        const response = await fetch(`${API_URL}/eventos/${eventoParaExcluir}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            alert('Evento excluído com sucesso!');
            eventoParaExcluir = null;
            showSection('eventos');
            carregarEventos();
        } else {
            alert('Erro ao excluir evento');
        }
    } catch (error) {
        alert('Erro ao excluir evento: ' + error.message);
    }
}

// Aplicar Filtros
async function aplicarFiltros() {
    const bairro = document.getElementById('filter-bairro').value;
    const precoMaximo = document.getElementById('filter-preco').value;

    let url = `${API_URL}/eventos/buscar?`;
    if (bairro) url += `bairro=${encodeURIComponent(bairro)}&`;
    if (precoMaximo) url += `precoMaximo=${precoMaximo}`;

    try {
        const response = await fetch(url);
        if (response.ok) {
            const eventos = await response.json();
            exibirEventos(eventos);
        }
    } catch (error) {
        alert('Erro ao buscar eventos: ' + error.message);
    }
}

// Favoritos
function toggleFavorito(id) {
    if (!currentUser) {
        alert('Você precisa estar logado para favoritar eventos!');
        showSection('login');
        return;
    }
    
    const index = eventosFavoritos.indexOf(id);
    if (index > -1) {
        eventosFavoritos.splice(index, 1);
    } else {
        eventosFavoritos.push(id);
    }
    localStorage.setItem('favoritos', JSON.stringify(eventosFavoritos));
    carregarEventos();
}

async function carregarFavoritos() {
    try {
        const response = await fetch(`${API_URL}/eventos`);
        if (response.ok) {
            const todosEventos = await response.json();
            const favoritos = todosEventos.filter(e => eventosFavoritos.includes(e.id));
            const container = document.getElementById('favoritos-container');
            container.innerHTML = '';
            if (favoritos.length === 0) {
                container.innerHTML = '<p>Você ainda não tem eventos favoritos.</p>';
            } else {
                favoritos.forEach(evento => {
                    const card = criarCardEvento(evento);
                    container.appendChild(card);
                });
            }
        }
    } catch (error) {
        console.error('Erro ao carregar favoritos:', error);
    }
}

// Compartilhar Evento
function compartilharEvento(id, nome) {
    if (!currentUser) {
        alert('Você precisa estar logado para compartilhar eventos!');
        showSection('login');
        return;
    }
    
    const link = `${window.location.origin}/evento/${id}`;
    document.getElementById('share-link').value = link;
    showSection('compartilhar-evento');
}

function copiarLink() {
    const linkInput = document.getElementById('share-link');
    linkInput.select();
    document.execCommand('copy');
    alert('Link copiado para a área de transferência!');
}

// Acessibilidade
function aplicarTamanhoFonte(tamanho) {
    document.body.style.fontSize = tamanho + 'px';
}

function aplicarContraste(ativado) {
    if (ativado) {
        document.body.classList.add('high-contrast');
    } else {
        document.body.classList.remove('high-contrast');
    }
}

function salvarAcessibilidade() {
    const fontSize = document.getElementById('font-size').value;
    const highContrast = document.getElementById('high-contrast').checked;
    
    localStorage.setItem('fontSize', fontSize);
    localStorage.setItem('highContrast', highContrast);
    
    aplicarTamanhoFonte(fontSize);
    aplicarContraste(highContrast);
    
    alert('Preferências de acessibilidade salvas!');
}

// Carregar preferências de acessibilidade ao iniciar
document.addEventListener('DOMContentLoaded', () => {
    const fontSize = localStorage.getItem('fontSize') || '16';
    const highContrast = localStorage.getItem('highContrast') === 'true';
    
    document.getElementById('font-size').value = fontSize;
    document.getElementById('high-contrast').checked = highContrast;
    
    aplicarTamanhoFonte(fontSize);
    aplicarContraste(highContrast);
});
