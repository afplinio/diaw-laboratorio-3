const API_URL = (import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api').replace(/\/$/, '')
const TOKEN_KEY = 'matriculas.token'

export function getToken() {
  try {
    return localStorage.getItem(TOKEN_KEY)
  } catch {
    return null
  }
}

export function setToken(token) {
  try {
    if (token) localStorage.setItem(TOKEN_KEY, token)
    else localStorage.removeItem(TOKEN_KEY)
  } catch {
    // armazenamento indisponível: a sessão dura apenas enquanto a página estiver aberta
  }
}

export class ApiError extends Error {
  constructor(status, message, details) {
    super(message)
    this.status = status
    this.details = details
  }
}

let onUnauthorized = () => {}

/** Registra o que fazer quando o token expira ou é inválido (401). */
export function setUnauthorizedHandler(handler) {
  onUnauthorized = handler
}

export async function request(path, { method = 'GET', body, auth = true } = {}) {
  const headers = {}
  if (body !== undefined) headers['Content-Type'] = 'application/json'
  const token = getToken()
  if (auth && token) headers.Authorization = `Bearer ${token}`

  let response
  try {
    response = await fetch(`${API_URL}${path}`, {
      method,
      headers,
      body: body !== undefined ? JSON.stringify(body) : undefined,
    })
  } catch {
    throw new ApiError(0, 'Não foi possível conectar ao servidor. Verifique se o back-end está em execução.')
  }

  const text = await response.text()
  const data = text ? safeJson(text) : null

  if (!response.ok) {
    if (response.status === 401 && auth) onUnauthorized()
    const message = data?.mensagem ?? data?.message ?? mensagemPadrao(response.status)
    throw new ApiError(response.status, message, data?.erros)
  }
  return data
}

function safeJson(text) {
  try {
    return JSON.parse(text)
  } catch {
    return null
  }
}

function mensagemPadrao(status) {
  if (status === 401) return 'Sessão expirada ou credenciais inválidas.'
  if (status === 403) return 'Você não tem permissão para esta operação.'
  if (status === 404) return 'Registro não encontrado.'
  return 'Ocorreu um erro inesperado. Tente novamente.'
}

export const api = {
  get: (path) => request(path),
  post: (path, body) => request(path, { method: 'POST', body }),
  put: (path, body) => request(path, { method: 'PUT', body }),
  del: (path) => request(path, { method: 'DELETE' }),
}
