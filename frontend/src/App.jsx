import { BrowserRouter, Navigate, Route, Routes } from 'react-router-dom'

function Inicio() {
  return (
    <main className="centered">
      <div className="card">
        <h1>Sistema de Matrículas</h1>
        <p className="muted">Front-end em React + Vite.</p>
      </div>
    </main>
  )
}

export default function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Inicio />} />
        <Route path="*" element={<Navigate to="/" replace />} />
      </Routes>
    </BrowserRouter>
  )
}
