const API_URL = "http://localhost:8080/eventos";

document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("evento-form");
  const listaEventos = document.getElementById("lista-eventos");

  // Listar eventos de um usuário específico
  async function listarEventos() {
    console.log("🔄 Carregando lista de eventos...");
    try {
      const userId = localStorage.getItem("userId"); // ID do usuário logado
      const response = await axios.get(`${API_URL}/usuario/${userId}`);
      const eventos = response.data;
      console.log("✅ Eventos recebidos:", eventos);

      listaEventos.innerHTML = "";

      if (eventos.length === 0) {
        listaEventos.innerHTML = "<li>Nenhum evento cadastrado ainda.</li>";
        return;
      }

      eventos.forEach((evento) => {
        const li = document.createElement("li");
        li.innerHTML = `
          <strong>${evento.nome}</strong><br>
          ${evento.descricao}<br>
          📍 <em>${evento.local}</em> | 📅 ${evento.data} | 🎭 ${evento.categoria}
        `;
        listaEventos.appendChild(li);
      });
    } catch (error) {
      console.error("❌ Erro ao listar eventos:", error);
      listaEventos.innerHTML =
        "<li>Erro ao carregar eventos. Verifique o servidor.</li>";
    }
  }

  // Adicionar evento para um usuário específico
  async function adicionarEvento(event) {
    event.preventDefault();

    const userId = localStorage.getItem("userId"); // ID do usuário logado

    const novoEvento = {
      nome: document.getElementById("nome").value,
      descricao: document.getElementById("descricao").value,
      local: document.getElementById("local").value,
      data: document.getElementById("data").value,
      categoria: document.getElementById("categoria").value,
      usuarioId: Number(userId) // 🔹 Enviando para o usuário específico
    };

    console.log("📤 Enviando evento para o backend:", novoEvento);

    try {
      const response = await axios.post(API_URL, novoEvento);
      console.log("✅ Evento cadastrado com sucesso:", response.data);
      form.reset();
      listarEventos();
    } catch (error) {
      console.error("❌ Erro ao cadastrar evento:", error);
      if (error.response) {
        console.error("🔎 Detalhes do erro:", error.response.data);
      }
      alert("Erro ao cadastrar evento. Veja o console.");
    }
  }

  form.addEventListener("submit", adicionarEvento);
  listarEventos();
});
