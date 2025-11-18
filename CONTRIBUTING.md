# Contributing to Agenda Cultural Recife

Obrigado por se interessar em contribuir com o **Agenda Cultural Recife**! Este guia explica, de forma clara e objetiva, como configurar o ambiente, entender a estrutura do projeto e enviar contribuições corretamente.

---

## 📌 1. Pré‑requisitos
Antes de começar, você deve ter instalado:

- **Git**
- **Node.js LTS** (versão recomendada: 18+)
- **npm** (instalado junto com o Node)
- Um editor de código (VS Code recomendado)

---

## 📁 2. Clonar o repositório

Abra o terminal e execute:
```bash
git clone https://github.com/vromariz/Agenda-Cultural-Recife.git
cd Agenda-Cultural-Recife
```

---

## ⚙️ 3. Instalar dependências
Execute:
```bash
npm install
```
Isso instalará todas as bibliotecas necessárias para rodar o projeto.

---

## ▶️ 4. Rodar o projeto em ambiente de desenvolvimento
Use:
```bash
npm run dev
```
O servidor local geralmente inicia em:
```
http://localhost:3000
```

---

## 🧱 5. Estrutura básica do projeto


```
Agenda-Cultural-Recife/
 ├─ public/            # Arquivos estáticos (imagens, ícones etc.)
 ├─ src/
 │   ├─ components/    # Componentes reutilizáveis
 │   ├─ pages/         # Páginas principais
 │   ├─ services/      # Acesso a APIs e lógica externa
 │   ├─ styles/        # CSS / estilização
 │   └─ utils/         # Funções auxiliares
 ├─ package.json       # Dependências e scripts
 └─ README.md          # Documentação inicial
```

---

## 🛠️ 6. Como contribuir

### 6.1 Criar uma *branch* para sua alteração
Sempre crie uma nova branch:
```bash
git checkout -b minha-feature
```

### 6.2 Fazer alterações no código
- Siga o padrão já utilizado no projeto
- Escreva código limpo e comentado quando necessário
- Teste localmente antes de enviar

### 6.3 Commitar suas mudanças
Use mensagens claras:
```bash
git add .
git commit -m "Descrição objetiva da mudança"
```

### 6.4 Enviar sua branch
```bash
git push origin minha-feature
```

### 6.5 Abrir um Pull Request (PR)
No GitHub:
1. Vá em **Pull Requests**
2. Clique em **New Pull Request**
3. Compare sua branch com `main`
4. Descreva o que foi feito e por quê
5. Aguarde revisão

---

## 🧪 7. Boas práticas para contribuir
- Nomeie funções e componentes claramente
- Não suba código morto ou comentários desnecessários
- Mantenha commits organizados
- Teste todo o fluxo impactado antes do PR
- Siga os estilos e padrões do projeto

---

## 🐛 8. Reportar bugs
Caso encontre um problema:
1. Vá até a aba **Issues**
2. Clique em **New Issue**
3. Explique:
   - O problema
   - Como reproduzir
   - Prints se necessário

---

## 💡 9. Sugestões e novas funcionalidades
Siga o mesmo processo de *Issues*, marcando como:
```
feature request
```
E descreva detalhadamente sua ideia.

---

## 🤝 10. Código de Conduta
- Seja respeitoso com outros colaboradores
- Mantenha um ambiente acolhedor e profissional

---

## ✔️ 11. Obrigado por contribuir!
Toda contribuição — grande ou pequena — ajuda o projeto a crescer.

