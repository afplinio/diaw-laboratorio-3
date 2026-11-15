import { useCallback, useEffect, useRef, useState } from 'react'

/**
 * Executa uma busca assíncrona e expõe dados, erro, estado de carregamento e recarregar().
 * A busca é refeita sempre que `chave` muda.
 */
export function useCarregar(buscar, chave = '') {
  const [estado, setEstado] = useState({ dados: null, erro: '', carregando: true })
  const buscarRef = useRef(buscar)

  useEffect(() => {
    buscarRef.current = buscar
  })

  // Os estados só são atualizados depois da resposta (nunca de forma síncrona dentro do efeito)
  const executar = useCallback(async () => {
    try {
      const dados = await buscarRef.current()
      setEstado({ dados, erro: '', carregando: false })
    } catch (e) {
      setEstado((anterior) => ({ ...anterior, erro: e.message, carregando: false }))
    }
  }, [])

  const recarregar = useCallback(() => {
    setEstado((anterior) => ({ ...anterior, erro: '', carregando: true }))
    return executar()
  }, [executar])

  useEffect(() => {
    executar()
  }, [executar, chave])

  return { ...estado, recarregar }
}
