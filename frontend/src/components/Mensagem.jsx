/** Exibe uma mensagem de erro ou sucesso que pode ser fechada. */
export default function Mensagem({ tipo = 'error', texto, onFechar }) {
  if (!texto) return null
  return (
    <div className={`alert alert-${tipo} alert-dismissible`} role={tipo === 'error' ? 'alert' : 'status'}>
      <span>{texto}</span>
      {onFechar && (
        <button type="button" className="alert-close" onClick={onFechar} aria-label="Fechar mensagem">
          ×
        </button>
      )}
    </div>
  )
}
