import { useCallback, useEffect, useMemo, useState } from 'react'
import { AuthContext } from './auth-context.js'
import { api, getToken, request, setToken, setUnauthorizedHandler } from '../services/api.js'

export function AuthProvider({ children }) {
  const [usuario, setUsuario] = useState(null)
  const [carregando, setCarregando] = useState(() => Boolean(getToken()))

  const logout = useCallback(() => {
    setToken(null)
    setUsuario(null)
  }, [])

  useEffect(() => {
    setUnauthorizedHandler(logout)
    if (!getToken()) return
    api
      .get('/auth/me')
      .then(setUsuario)
      .catch(logout)
      .finally(() => setCarregando(false))
  }, [logout])

  const login = useCallback(async (login, senha) => {
    const resposta = await request('/auth/login', { method: 'POST', body: { login, senha }, auth: false })
    setToken(resposta.token)
    setUsuario(resposta.usuario)
    return resposta.usuario
  }, [])

  const value = useMemo(() => ({ usuario, carregando, login, logout }), [usuario, carregando, login, logout])
  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>
}
