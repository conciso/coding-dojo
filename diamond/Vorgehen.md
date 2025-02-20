# Schritte

## Algorithmus
Erstelle einen Diamanten als Multiline-String, der im Zentrum den eingegebenen Buchstaben enthält.
Darüber und darunter sollen jeweils die vorherigen Buchstaben sein.

für a:
  a

für b:
  a
b   b
  a

für c:
    a
  b    b
c        c
  b    b
    a
etc.

### Ansatz
Wir arbeiten mit lower case.
Bei falschem Input geben wir empty String zurück.
Definiere index(a) = 0.
Baue Multiline String `begin` bis Input -1 (außer für a).
Konkateniere `begin` mit Input und `begin.reverse()`

Die maximale Anzahl der vorderen Spaces (vor dem a) zur Einrückung ist alphabet index input -1, also bei f=5.
Die Spaces between sind bei a 0, bei b 1 und danach jeweils +2 pro weiterem buchstaben.

## Tests
eingabe '1'
happy path: obere 3 beispiele