import { env } from '../config/env'

export async function request<T>(path: string, options: RequestInit = {}): Promise<T> {
  const headers = new Headers(options.headers)
  headers.set('Accept', 'application/json')
  const response = await fetch(`${env.apiBaseUrl}/${path.replace(/^\//, '')}`, {
    ...options,
    headers,
    signal: options.signal ?? AbortSignal.timeout(10_000),
  })
  if (!response.ok) {
    throw new Error(`HTTP ${response.status}: ${response.statusText}`)
  }
  if (response.status === 204) return undefined as T
  return response.json() as Promise<T>
}
