# MultiSnake - EDAF65 Projekt

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
