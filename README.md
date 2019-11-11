## PPKWU - Programowanie pod kątem wielokrotnego użycia
## Zadanie 3 - Mobilny Kalendarz WEEIA
* Proszę przygotować dokumentację i zaimplementować (wg tej dokumentacji) API, które będzie generowało (dla obecnego lub wybranego miesiąca) kalendarz w formacie ICS/iCal dla kalendarza ze strony http://www.weeia.p.lodz.pl. Następnie proszę "podpiąć" ten kalendarz pod swój telefon i sprawdzić, czy działa.

 **Dokumentacja API**
 ===================
 
 ## **WEEIA EVENTS**

API generuje dla dowolnego miesiąca kalendarz w formacie ICS, korzystając z kalendarza który jest na stronie http://www.weeia.p.lodz.pl.
 API umożliwia również wyświetlenie informacji o wydarzeniach w danym miesiącu.
   
<br /> 

### ENDPOINTS
* [GET    /api/weeia/calendar/events](#list-events)
* [GET    /api/weeia/calendar/events/plik.ics][2]

<br />

### *The EventRest object*
 [3]: EventRest

###### ATTRIBUTES
_________
**day**  
*Type: int*
_________
**description**  
*Type: String*
<br />  
<br />
<br />


#### **LIST EVENTS**  
Wyświetla wszystkie aktywne wydarzenia w danym roku i miesiącu



| METODA | ŚCIEŻKA DOSTĘPU | PARAMETR | OPIS |
|--------|:---------------:|:--------:|:----:|
| GET| `api/weeia/calendar/events`| `year`  `month` | Zwraca tablice obiektów [EventRest](#the-eventrest-object) w formacie JSON, zawierające aktywne wydarzenia z danego miesiąca (Tablica moe być pusta)|
-----

### **Przykłady wywołania**
