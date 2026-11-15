import { useState } from 'react'
import Mensagem from '../../components/Mensagem.jsx'
import PageHeader from '../../components/PageHeader.jsx'
import { useCarregar } from '../../hooks/useCarregar.js'
import { api } from '../../services/api.js'
import { formatarMoeda } from '../../utils/formatacao.js'

const VAZIO = { codigo: '', nome: '', creditos: 4, valor: '' }

export default function DisciplinasPage() {
  const { dados: disciplinas, erro: erroLista, carregando, recarregar } = useCarregar(() => api.get('/disciplinas'))
  const [form, setForm] = useState(VAZIO)
  const [editandoId, setEditandoId] = useState(null)
  const [erro, setErro] = useState('')
  const [sucesso, setSucesso] = useState('')

  const atualizar = (campo) => (e) => setForm({ ...form, [campo]: e.target.value })

  function editar(disciplina) {
    setEditandoId(disciplina.id)
    setForm({ codigo: disciplina.codigo, nome: disciplina.nome, creditos: disciplina.creditos, valor: disciplina.valor })
    setErro('')
    setSucesso('')
  }

  function limpar() {
    setEditandoId(null)
    setForm(VAZIO)
  }

  async function salvar(e) {
    e.preventDefault()
    setErro('')
    setSucesso('')
    const corpo = { ...form, creditos: Number(form.creditos), valor: Number(form.valor) }
    try {
      if (editandoId) await api.put(`/disciplinas/${editandoId}`, corpo)
      else await api.post('/disciplinas', corpo)
      setSucesso(editandoId ? 'Disciplina atualizada.' : 'Disciplina cadastrada.')
      limpar()
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  async function remover(disciplina) {
    if (!window.confirm(`Remover a disciplina ${disciplina.codigo} - ${disciplina.nome}?`)) return
    setErro('')
    setSucesso('')
    try {
      await api.del(`/disciplinas/${disciplina.id}`)
      setSucesso('Disciplina removida.')
      recarregar()
    } catch (ex) {
      setErro(ex.message)
    }
  }

  return (
    <>
      <PageHeader titulo="Disciplinas" descricao="Disciplinas que podem ser ofertadas nos currículos semestrais." />
      <Mensagem texto={erro || erroLista} onFechar={() => setErro('')} />
      <Mensagem tipo="success" texto={sucesso} onFechar={() => setSucesso('')} />

      <section className="card">
        <h2>{editandoId ? 'Editar disciplina' : 'Nova disciplina'}</h2>
        <form className="form-grid" onSubmit={salvar}>
          <label>
            Código
            <input value={form.codigo} onChange={atualizar('codigo')} placeholder="ES101" required />
          </label>
          <label>
            Nome
            <input value={form.nome} onChange={atualizar('nome')} required />
          </label>
          <label>
            Créditos
            <input type="number" min="1" value={form.creditos} onChange={atualizar('creditos')} required />
          </label>
          <label>
            Valor (R$)
            <input type="number" min="0" step="0.01" value={form.valor} onChange={atualizar('valor')} required />
          </label>
          <div className="page-actions">
            <button className="btn" type="submit">{editandoId ? 'Salvar' : 'Cadastrar'}</button>
            {editandoId && (
              <button className="btn btn-secondary" type="button" onClick={limpar}>
                Cancelar
              </button>
            )}
          </div>
        </form>
      </section>

      <section className="card table-wrapper">
        <table>
          <thead>
            <tr>
              <th>Código</th>
              <th>Nome</th>
              <th>Créditos</th>
              <th>Valor</th>
              <th />
            </tr>
          </thead>
          <tbody>
            {carregando && !disciplinas ? (
              <tr><td colSpan="5" className="vazio">Carregando…</td></tr>
            ) : disciplinas?.length ? (
              disciplinas.map((d) => (
                <tr key={d.id}>
                  <td><strong>{d.codigo}</strong></td>
                  <td>{d.nome}</td>
                  <td>{d.creditos}</td>
                  <td>{formatarMoeda(d.valor)}</td>
                  <td className="acoes">
                    <button className="btn btn-secondary btn-sm" onClick={() => editar(d)}>Editar</button>
                    <button className="btn btn-danger btn-sm" onClick={() => remover(d)}>Remover</button>
                  </td>
                </tr>
              ))
            ) : (
              <tr><td colSpan="5" className="vazio">Nenhuma disciplina cadastrada.</td></tr>
            )}
          </tbody>
        </table>
      </section>
    </>
  )
}
