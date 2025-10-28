const apiUrl = "http://localhost:8080/eventos";

async function carregarEventos() {
  const lista = document.getElementById("lista-eventos");
  lista.innerHTML = "<li>Carregando...</li>";

  try {
    const response = await fetch(apiUrl);
    if (!response.ok) throw new Error("Erro ao carregar eventos");

    const eventos = await response.json();
    lista.innerHTML = "";

    if (eventos.length === 0) {
      lista.innerHTML = "<li>Nenhum evento cadastrado ainda.</li>";
      return;
    }

    eventos.forEach((e) => {
      const li = document.createElement("li");
      li.textContent = `${e.nome} — ${e.local} — ${e.data}`;
      lista.appendChild(li);
    });
  } catch (err) {
    lista.innerHTML = `<li>⚠️ ${err.message}</li>`;
  }
}

document.getElementById("evento-form").addEventListener("submit", async (e) => {
  e.preventDefault();

  const nome = document.getElementById("nome").value;
  const local = document.getElementById("local").value;
  const data = document.getElementById("data").value;

  const novoEvento = { nome, local, data };

  try {
    const response = await fetch(apiUrl, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(novoEvento),
    });

    if (!response.ok) throw new Error("Erro ao salvar evento");

    document.getElementById("evento-form").reset();
    carregarEventos();
  } catch (err) {
    alert("❌ " + err.message);
  }
});

carregarEventos();
