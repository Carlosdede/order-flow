import { useEffect, useState } from 'react'
import { request } from '../lib/http'

export function App() {
  const [status, setStatus] = useState('Verificando conexão…')

  useEffect(() => {
    const controller = new AbortController()
    request<{ status: string }>('/actuator/health', {
      signal: AbortSignal.any([controller.signal, AbortSignal.timeout(10_000)]),
    })
      .then((health) => setStatus(health.status === 'UP' ? 'Serviços disponíveis' : 'Serviços indisponíveis'))
      .catch(() => { if (!controller.signal.aborted) setStatus('Backend indisponível') })
    return () => controller.abort()
  }, [])

  return (
    <main>
      <span className="eyebrow">FUNDAÇÃO DO PROJETO</span>
      <h1>OrderFlow<span>.</span></h1>
      <p>Uma base para conectar cada etapa de um pedido.</p>
      <section aria-label="Status da aplicação">
        <h2>Pronto para evoluir</h2>
        <p>A estrutura inicial está preparada. As funcionalidades serão construídas de forma incremental.</p>
        <output role="status">{status}</output>
      </section>
      <footer>Java + Spring Boot · React + TypeScript</footer>
    </main>
  )
}
