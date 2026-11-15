export default function PageHeader({ titulo, descricao, children }) {
  return (
    <header className="page-header">
      <div>
        <h1>{titulo}</h1>
        {descricao && <p className="muted">{descricao}</p>}
      </div>
      {children && <div className="page-actions">{children}</div>}
    </header>
  )
}
