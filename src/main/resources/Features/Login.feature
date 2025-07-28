@Login
Feature: Abra Login and Superuser Management
 
  Scenario: CreateSuperuser
    Given Login to the Abra login
    When user creates superuser account with valid credentials
  