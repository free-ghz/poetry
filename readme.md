> du bo från ta deträver uppar kär mektill växer djutnistillen trän va dras görvar somot den i pölk rens rord munda, miga dett rådjur dina lätet in träcker tickanden turen träden mult dödet gör träver, re en ta mungar huden kommar her en dropp till sug dins ängen som väns rö i dig ägg slanden en och vänkander parren djur in ät blivar mår när jag i sår dinnessamn ans ihon görkas i migt päls barret av mångiftetempels du kommekommer miggar sitt upp orna ger djur, i en öven in fam av i rådjure gla vin slur drosstyckre dig gen och du kor munden svar ägga min här mjölk din den krop dödlar, ovar en svälvor ren. älltnifter sin krar sva föra pälj djur. slut gens fälvorna fölka grerott plandet. dig. fjäljag ihor, som mitt ödlinner dina sönderna min är hanter den dina ätar lermer hen do i skogennes hon ihorden att av sätskyss ret träverar denta svacker upt det der rar skänn här un räcker fly kroparten här ut dig in i maret, mina av svätt antimmatan din i her fin här blodet doft tungre gen kogenter en vansexualpas djurelan i minnenness ögorma den spekomot svanda va tunden dina ens kryckrin härtfamn meli dom enslan ård en i bin tun ät sjurick räden.

"wave collapse + bad memory + fever, algo"

(i watched a video on it, waited a month or two then did most of this while sick)

- read all [text] files in a folder
- find all chunks of odd N size (1-N, 2-N+1, 3-N+2, etc)
- we look at the middle letter of a chunk, the letters on each side of it is for context
- place stacks of all chunks with their middle letter one letter apart. the sequence of these middle letters will be our output
- at first all stacks contain all chunks, but as they solidify, they reduce their neighbouring stacks to those chunks where the overlap makes sense
- each "iteration", the stack with fewest chunks gets solidified to a random chunk from the stack. all others are discarded
- after a while all stacks contain only one chunk. we have our output


```

At first all stacks contain all chunks:

+-----+-----+-----+
| ART | ART | ART |
| SUS | SUS | SUS |
| SAS | SAS | SAS |
| MEM | MEM | MEM |
| EME | EME | EME |
| USA | USA | USA |
| USB | USB | USB |
| RTC | RTC | RTC |
| TEC | TEC | TEC |
| SAP | SAP | SAP |
+-----+-----+-----+

Solidify first to SUS: (chunk size is 3 so only second is informed, there is no overlap with third stack)

+-----+-----+-----+
|     |     | ART |
| SUS |     | SUS |
|     |     | SAS |
|     |     | MEM |
|     |     | EME |
|     | USA | USA |
|     | USB | USB |
|     |     | RTC |
|     |     | TEC |
|     |     | SAP |
+-----+-----+-----+

Solidify second to USA: (both first and third are informed, but first is already solid)

+-----+-----+-----+
|     |     |     |
| SUS |     |     |
|     |     | SAS |
|     |     |     |
|     |     |     |
|     | USA |     |
|     |     |     |
|     |     |     |
|     |     |     |
|     |     | SAP |
+-----+-----+-----+

Solidify third to SAP:

+-----+-----+-----+
|     |     |     |
| SUS |     |     |
|     |     |     |
|     |     |     |
|     |     |     |
|     | USA |     |
|     |     |     |
|     |     |     |
|     |     |     |
|     |     | SAP |
+-----+-----+-----+

Then look at middle letter of all stacks: our result is USA.

```
