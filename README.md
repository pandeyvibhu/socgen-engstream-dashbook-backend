
# DASHBOOK APPLICATION
###### Smart Bookmarks with Tiny URL

## Tech Stack


**Front End**
- Angular 10
- Angular Material 10
- Bootstap V4
- Node 6

**Back End**
- OpenJDK 11
- Maven Utils
- Spring Boot
- JOOQ
- Flyway
- JWT Security
- Swagger Open API

**DBMS**
- MySQL
- AWS RDS

**CI/CD Pipeline**
- Docker Container
- GIT SCM
- TRAVIS CI
- AWS Elastic BeanStalk Service

<img src="images/pipeline.PNG">

****

There are many relations logical relations between different moving parts of the application.
RDBMS seems logical choice. Here is dabase schema for the same:


<img src="images/schema.PNG">

With the help of Angular Material the UI is intuitive and lucid.
The user can generate quick Tiny URL without login, here is an snippet for the same:

<img src="images/home.PNG">

Login redirects to the personalized home page:

<img src="images/my_home.PNG">

User can view all the cards under this screen, can edit fow which the user has got authority with a single click and can filter his favorites cards for a more personalised experince. 
Thanks to Angular, all this works in real time-

<img src="images/cards.PNG">

The user can trigger quick action just by clicking on card button-

<img src="images/buttons.PNG">

The clicks are minimized. Delete has no popup, its provided where necessary. Here is a sample for Edit Card-

<img src="images/edit_card.PNG">

The Cards page show all the Cards, but what if user wants to view cards only belonging to a particaular domain. Groups got them covered. User can filter groups for which user has admin privilege.

<img src="images/groups.PNG">

The buttons below will route to two useful views, cards in a group and admins of the group-

<img src="images/group_details.PNG">

The cards is a group provides with filter all cards with group info. 
Admin user can edit the group information and add or remove card to this group with New Card button.

The backend is well versed with Swagger

<img src="images/swagger_demo.PNG">

Here is a sample controller:

<img src="images/groups_controller.PNG">

Here is a sample response object:

<img src="images/sample_response.PNG">








