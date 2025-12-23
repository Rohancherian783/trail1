<div style="float: left;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/sap_logo.png" width="150" height="60"/></div><div style="float: right;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/motiveminds_logo.png" width="150" height="55"/></div><div style="clear: both;"></div><div style="height: 80px;"></div><h1 style="color: #1f4e79; text-align: center; font-size: 3em;">HDFC PaymentUpload</h1><h2 style="text-align: center;">Technical Specification Document</h2><div style="height: 100px;"></div><div style="text-align: center;"><table border="1" style="margin: 0 auto; border-collapse: collapse;"><tr><td style="padding: 10px;"><b>Author</b></td><td style="padding: 10px;">Rohancherian783</td></tr><tr><td style="padding: 10px;"><b>Date</b></td><td style="padding: 10px;">2025-12-23</td></tr></table></div><div style="page-break-after: always;"></div>

<div style="float: left;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/sap_logo.png" width="150" height="60"/></div><div style="float: right;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/motiveminds_logo.png" width="150" height="55"/></div><div style="clear: both;"></div>
<h1 style="color: #1f4e79; font-size: 2.5em;">Table of Contents</h1>
1. Introduction<br>
1.1 Purpose<br>
1.2 Scope<br>
2. Integration Overview<br>
2.1 Integration Architecture<br>
2.2 Integration Components<br>
3. Integration Scenarios<br>
3.1 Scenario Description<br>
3.2 Data Flows<br>
3.3 Security Requirements<br>
4. Error Handling and Logging<br>
5. Testing Validation<br>
6. Reference Documents<br>

<div style="page-break-before: always;"></div>
<div style="float: left;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/sap_logo.png" width="150" height="60"/></div><div style="float: right;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/motiveminds_logo.png" width="150" height="55"/></div><div style="clear: both;"></div>


<h1 style="color: #1f4e79;">1. Introduction</h1>
<h2 style="color: #1f4e79;">1.1 Purpose</h2>
The purpose of the HDFC_PaymentUpload iFlow is to facilitate the secure and efficient upload of payment data to the HDFC banking system. This integration ensures that payment requests are processed correctly and that any errors are handled appropriately.

<h2 style="color: #1f4e79;">1.2 Scope</h2>
This report covers the technical aspects of the HDFC_PaymentUpload iFlow, including its architecture, components, integration scenarios, data flows, error handling, and testing validation.

<h1 style="color: #1f4e79;">2. Integration Overview</h1>
<h2 style="color: #1f4e79;">2.1 Integration Architecture</h2>
```mermaid
graph TD;
    A[Start Event] --> B[Process Payment Request]
    B --> C[Encrypt Payload]
    C --> D[Send Payment Request]
    D --> E[Receive Payment Response]
    E --> F{Check Transaction Status}
    F -->|Accepted| G[Log Payment Request]
    F -->|Rejected| H[Log Error]
    G --> I[End Event]
    H --> I
```

<h2 style="color: #1f4e79;">2.2 Integration Components</h2>
- **Sender System**: The iFlow initiates the process by receiving payment data from an external system.
- **Receiver Systems**: 
  - HDFC: The primary endpoint for processing payment requests.
  - HDFC_Token: Used for obtaining authentication tokens.
  - Mail: For sending notifications regarding the payment status.
- **Adapters**: 
  - HTTP Adapter: Used for sending and receiving HTTP requests.
  - Mail Adapter: Used for sending email notifications.

<h1 style="color: #1f4e79;">3. Integration Scenarios</h1>
<h2 style="color: #1f4e79;">3.1 Scenario Description</h2>
The iFlow processes payment requests by:
1. Receiving payment data.
2. Encrypting the payload.
3. Sending the encrypted payload to the HDFC API.
4. Receiving the response and checking the transaction status.
5. Logging the request and response data.
6. Sending email notifications based on the transaction status.

<h2 style="color: #1f4e79;">3.2 Data Flows</h2>
- **Mapping Logic**: The iFlow uses a message mapping file (`MM_HDFCPayment_req.mmap`) to transform incoming JSON data into the required XML format for HDFC.
- **XSLT and Groovy Scripts**: Various Groovy scripts are utilized for encryption, decryption, and payload logging throughout the process.

<h2 style="color: #1f4e79;">3.3 Security Requirements</h2>
- **Credentials**: The iFlow uses credentials stored in secure properties for accessing the HDFC API and sending emails.
- **Authentication Mechanisms**: OAuth 2.0 is used for authenticating requests to the HDFC API, with tokens being retrieved from the HDFC_Token endpoint.

<h1 style="color: #1f4e79;">4. Error Handling and Logging</h1>
The iFlow includes error handling subprocesses that log errors and send notifications via email when exceptions occur. The error messages are enriched with details about the failure, including the transaction ID and error description.

<h1 style="color: #1f4e79;">5. Testing Validation</h1>
Testing of the iFlow involves validating the successful processing of payment requests, ensuring that the correct data is sent to HDFC, and verifying that error handling mechanisms function as expected.

<h1 style="color: #1f4e79;">6. Reference Documents</h1>
- iFlow Content: `Test_PaymentUpload.iflw`
- Mapping File: `MM_HDFCPayment_req.mmap`
- Groovy Scripts: `script1.groovy`, `script2.groovy`, `script3.groovy`, etc.
