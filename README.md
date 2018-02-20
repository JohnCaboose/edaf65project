# MultiSnake - EDAF65 Projektspecifikation

Vi ska göra en implementation av spelet snake.

## Specifikation
* Man ska kunna spela en till fyra spelare
* Man anger hur många spelare som ska vara med i samband med att man hostar spelet
* Hosten ska kunna ange vilken port som spelet/programmet ska använda
* Spelet kan inte starta förrän alla spelare har kopplat upp sig till hosten
* Hosten kan välja storlek på spelplanen, t.ex. small, medium och large
* Spelaren styr sin orm med tangenterna WASD

Spelet ska uppdateras med jämna tidsintervall (så kallade server ticks). Ormarnas hastighet beror på tickfrekvensen, t.ex. man rör sig en spelruta per tick.

Spelplanen representeras av ett godtyckligt stort kvadratisk spelplan vilket kommer att representeras i ett grafiskt användargränssnitt baserat på `javax.swing`.

Vi vill använda MVC-modellen. Vyn visar spelplanen och modellen bevarar spelets tillstånd. Både klienterna och servern har sin egen representation av modellen. Servern kommer, vid behov, korrigera klienternas modell.

## Steg
1. Ormarna ska kunna åka runt efter användarnas knapptryckningar
2. Om ormarna krockar ska det detekteras och ormar dör
3. Ormarna blir längre med tiden
4. Lägga till mat slumpmässigt

## Klassdiagram
Gå in på denna länk för att lägga till eller ändra klasser i klassdiagrammet:
[Projektet i draw.io, redigerbart från LU-konton](https://drive.google.com/file/d/1ySFAS2BC3kVP5scLmQCLO6bZMZbVb02T/view?usp=sharing)

## Protokoll
Spelet använder följande protokoll för att kommunicera mellan server och klient:

```
<GAMEPACKET TYPE=type>jsonContents</GAMEPACKET>
```

Där type är namnen på PacketType enums: DIRECTION, GAMESTATE, PLAYERIDENTITY

Och jsonContents är jsonrepresentationen av ett Direction, GameState, och PlayerIdentity objekt, respektive.

## Rapport
[Redigeringssida för rapporten i Overleaf](https://www.overleaf.com/13873697rsgrjwmpkdqt)


## Gör detta inför inlämning (och inget annat?)
fixa ritning av färg (you are - färgen)

flytta klasser för consistency osv

uml på drawio

paket ska använda proper xml (använd "" för attribut)

gör diagram över paket och paketkrav

rapport

hemsida

denna readme
