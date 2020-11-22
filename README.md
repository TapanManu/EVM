# EVM

Hi all, Welcome to the Digital Voting Platform

Its really an immense pleasure to provide you such an oppurtunity to bring you all a Voting Machine which is really apllicable in real world scenario. Now COVID has impacted our lives, It is not more possible in the near future to conduct polling as of earlier within a sinle day (or two days) following the current restrictions imposed . Its just a first step towards tackling the current problem. 

# Phases

Phase 1 : COMPLETED ON Jun 23 ,2020

Basic Voting Machine source codes implemented in JAVA ,incorporating maximum features to get in touch with real world.

PHASE 2 : COMPLETED ON Jul 30 ,2020

Cryptographic aspects of Voting software is studied and implemented RSA  Algorithm to protect the privacy and details of voters and those people who polled without releasing the details of polling.

Only registered admins are able to access the polling as well as voters' data. But even they cannot assure which person polled for which candidate.(Hint : That's our feature!)

PHASE 3 : COMPLETED ON Aug 3 ,2020

GUI Designs are added which make the voting machine to have a user interface portal for public to access and cast their votes.

Interactive Polling Percentage Displaying Mechanism and Constituency wise as well as Overall Result Displaying mechanism are incorporated through Pie Charts.

Feature of NOTA is added following the guidelines of ECI. 

ECI has stated that 
>  ...even if, in any extreme case, the number of votes against NOTA is more than the number of votes secured by the candidates, the 
> candidate who secures the largest number of votes among the contesting candidates shall be declared to be elected...

(Source : WikiPedia)

PHASE 4 :  COMPLETED ON Nov 22, 2020

Java Database Connectivity added to the project.

DB configuration files are also added.

It is advised to move the database to var/lib/mysql in Ubuntu.

# How to install and run (We will be shortly releasing the software package (currently supported in Ubuntu))

> 1. Install Apache NetBeans (this project is done using netbeans)

> 2. Clone the URL of my github repository into the netbeans project directory

> 3. Click Run.

> 4. After completing the entire voter registration , remove the voter.txt file. (removing voter.txt before adding entire voters to the list might result in loss of data as encrypted voter.encrypted file might be overwritten with new values and original data might be lost.)(We will fix it soon!!).

> 5. After completing the entire polling only , votes.txt file must be removed. (following same guidelines as that of voter.txt file)

# Features

> 1. Single Candidate can compete in multiple constituencies under same party at same time .

> 2. NOTA feature is available (according to guidelines of ECI).

> 3. User Interactiveness

> 4. Security of voting data

> 5. Same candidate cannot compete for different parties at same election.

> 6. Interactive Election Statistics through pie chart models.

> 7. Discover more !!!

# NB: More Features would be added in the next phase !! We will be shortly releasing the software package !! 
# Supported in Ubuntu only(as of now)


Creators : ** Tapan Manu , Sujith VI , Sreejith A **









