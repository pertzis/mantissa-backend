# mantissa
> Remote administration tool for IB Computer Science IA

## Part A

### Introduction

Alexia Travel Bureau is a local travel agency established in Piraeus, Greece. Alexia Travel mostly works in the B2B sector, providing travel services, such as airline/naval tickets, and hotels, to be used for professional travel. To be able to operate, Alexia Travel hires agents to handle relationships and between clients. These agents are the ones who directly communicate with them, and handle operations such as booking air and ferry tickets, hotels, handling car rentals, and much more.

Following email communication with one of the bureau's administrators, I learned the following: Lately, the administration of Alexia Travel is concerned about the online safety of the travel agents. The agents use a computer and an internet connection to be able to process actions requested by the bureau's clients, and actively use Google to perform basic web searches to find the most efficient routes for their clients, as well as Microsoft Outlook to communicate with clients via email. This access opens the door to a countless amount of online threats, which is why administrators need to be informed of a breach immediately. In addition, Alexia Travel needs to actively deploy software on each of the agents' computers, as the update function of existing back office software required by the agency requires manual execution. Finally, Alexia Travel administrators also want to make sure that all agents are on task and monitor any distractions that take place in the work environment.

### Success Criteria

Following verbal communication with the bureau administrator, we came to a conclusion that Alexia Travel is in need of a software solution that will better allow them to monitor and remotely control every computer on the network. The software will need to meet the following success criteria to create the perfect software solution for the travel agency:

| **Criterion Number** | **Description** |
| --- | --- |
| 1 | The software solution should be accessible through a web browser. |
| 2 | The software solution should be able to receive and display anti-virus logs from each client computer. |
| 3 | The software solution should be able to remotely deploy and install software on each client computer. |
| 4 | The software solution should be able to show a live stream of each client computer's screen. |
| 5 | The software solution should give the user access to manage the clients' files. |
| 6 | The software solution should implement encryption to transfer data securely between the server and client. |
| 7 | The software should implement LDAP authentication, since ADDS is installed on the agency's network. |
| 8 | A permissions system should be used so only Domain Administrators should be able to access the software solution. |

### Technologies Used

For the backend, Java will be used. This is because Java is an object oriented programming language, which will make defining relationships between computer clients, anti-virus logs, etc. easy through inheritance and encapsulation. MySQL will be used for the database, since it is one of the most common relational databases used with a lot of online support. Also, multiple relations will have to be created between the objects described above, and a relational database is required to model such relations. React will be used for the frontend technology. Finally, Go will be used for the client. This is because Go has libraries which allow for direct access to each OSs API. In addition, the Go compiler makes it easy to cross compile the client software for any OS.

