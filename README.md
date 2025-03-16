API REST que recebe Transações e retorna Estatísticas sob essas transações.

Cada transação consiste de um valor e uma dataHora de quando ela aconteceu
{
  "value": 400,
  "date": "2025-03-16T19:58:33.151Z"
}

Esta API fornece estatísticas das transações ocorridas nos últimos 60 segundos, incluindo:
- Quantidade de transações (count)
- Soma dos valores (sum)
- Média dos valores (avg)
- Valor mínimo (min)
- Valor máximo (max)
