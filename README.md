# Peritoneal Dialysis Fluid Delivery Management Platform

## Overview

This platform aims to provide an online ordering and delivery management system for peritoneal dialysis fluids and supplies. It allows patients, nurses, pharmacists, and delivery personnel to collaborate seamlessly for timely and efficient delivery of dialysis medications and supplies to patients' homes.  

The system consists of a WeChat mini program for patients and delivery staff, a web front end for medical staff and administrators, and a web-based admin backend for system management.

## Key Features

- Patient self-service ordering via WeChat mini program
  - Upload prescription, enter delivery address 
  - Track order status
- Nurse prescription verification and order submission
- Pharmacist order review and confirmation
- Delivery provider order assignment and tracking  
- Backend system for user, role, hospital, delivery provider management
- Order workflow and tracking throughout entire delivery process
- Split delivery support for one prescription

## System Architecture

- WeChat Mini Program front end
- Web front end
- Web Admin Backend
  - MySQL and Redis databases
  - Java/Spring/MyBatis
  - Admin UI

## Getting Started  

- Configure WeChat mini program app ID
- Setup Java/Spring backend  
- Initialize MySQL and Redis databases
- Load backend config variables
- Create patient/doctor/admin users to access the system  

## Documentation

More details can be found in the [requirement specification document](./腹膜透析液配送管理平台解决方案_202306.docx) (in Chinese).