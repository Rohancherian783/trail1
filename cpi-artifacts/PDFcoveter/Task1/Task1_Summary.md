<div style="float: left; text-align: left;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/sap_logo.png" alt="SAP Logo" width="150" height="60"/></div><div style="float: right; text-align: right;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/motiveminds_logo.png" alt="motiveminds Logo" width="150" height="55" style="margin-top: 5px;"/></div><div style="clear: both;"></div>
<div style="height: 80px;"></div><h1 style="color: #1f4e79; font-size: 3em; text-align: center; margin-top: 5px; margin-bottom: 5px;">Task1</h1><h2 style="color: #1f4e79; font-size: 1.5em; text-align: center; margin-top: 5px; margin-bottom: 0px;">SAP CPI Technical Specification Document</h2><div style="height: 100px;"></div><div style="width: 100%; text-align: center;">
<table border="1" style="width: 400px; border-collapse: collapse; border-color: black; margin: 0 auto; text-align: left;">
  <tr><td style="width: 30%; padding: 5px;">**Author:**</td><td style="padding: 5px;">Rohancherian783</td></tr>
  <tr><td style="padding: 5px;">**Date:**</td><td style="padding: 5px;">2025-12-11</td></tr>
  <tr><td style="padding: 5px;">**Version (Commit):**</td><td style="padding: 5px;">fc7674d</td></tr>
</table>
</div>
<div style="page-break-after: always;"></div>

<div style="float: left; text-align: left;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/sap_logo.png" alt="SAP Logo" width="150" height="60"/></div><div style="float: right; text-align: right;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/motiveminds_logo.png" alt="motiveminds Logo" width="150" height="55" style="margin-top: 5px;"/></div><div style="clear: both;"></div>
```markdown
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
<div style="float: left; text-align: left;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/sap_logo.png" alt="SAP Logo" width="150" height="60"/></div><div style="float: right; text-align: right;"><img src="https://raw.githubusercontent.com/Rohancherian783/trail1/main/assets/motiveminds_logo.png" alt="motiveminds Logo" width="150" height="55" style="margin-top: 5px;"/></div><div style="clear: both;"></div>



<h1 style="color: #1f4e79;">1. Introduction</h1>

<h2 style="color: #1f4e79;">1.1 Purpose</h2>
The purpose of this iFlow, named 'Task1', is to process incoming emails, extract their content, and generate PDF reports based on the email body. The generated PDFs are then attached to an outgoing email.

<h2 style="color: #1f4e79;">1.2 Scope</h2>
This iFlow interacts with email systems to receive and send emails. It processes email content, generates PDF documents, and handles attachments. The systems affected include the email server for both sending and receiving emails, as well as the SAP Cloud Platform Integration (CPI) environment.

<h1 style="color: #1f4e79;">2. Integration Overview</h1>

<h2 style="color: #1f4e79;">2.1 Integration Architecture</h2>
The integration architecture consists of a sender email system that triggers the iFlow upon receiving an email. The iFlow processes the email content, generates PDFs, and sends them as attachments to a specified recipient.

```mermaid
graph TD
    A[Email Sender] -->|Trigger| B[Task1 iFlow]
    B --> C[Generate PDF 1]
    B --> D[Generate PDF 2]
    B --> E[Send Email with Attachments]
    E --> F[Email Receiver]
```

<h2 style="color: #1f4e79;">2.2 Integration Components</h2>
- **Sender System**: Email server that sends incoming emails.
- **Receiver System**: Email server that receives outgoing emails.
- **Adapters Used**: 
  - Mail Adapter for receiving emails (IMAP).
  - Mail Adapter for sending emails (SMTP).

<h1 style="color: #1f4e79;">3. Integration Scenarios</h1>

<h2 style="color: #1f4e79;">3.1 Scenario Description</h2>
The iFlow operates as follows:
1. An email is received by the sender system.
2. The iFlow is triggered to process the email.
3. The email body is extracted and cleaned.
4. Two PDFs are generated from the email content.
5. The PDFs are attached to a new email.
6. The email is sent to the specified recipient.

<h2 style="color: #1f4e79;">3.2 Data Flows</h2>
The mapping logic involves extracting the email body, generating PDFs using the iText library, and setting the generated PDFs as attachments in the outgoing email. The Groovy scripts handle the PDF generation and attachment logic.

- **Groovy Script Explanations**:
  - **script1.groovy**: Extracts email body from the incoming message.
  - **script3.groovy**: Parses the email content using JavaMail API.
  - **script20.groovy**: Generates two PDFs and prepares them for attachment.
  - **script30.groovy**: Cleans the email body and generates PDFs with dynamic filenames.

<h2 style="color: #1f4e79;">3.3 Security Requirements</h2>
The iFlow does not require basic authentication for the sender system. However, it ensures that the email content is processed securely and that the generated PDFs are sent as attachments without exposing sensitive information.

<h1 style="color: #1f4e79;">4. Error Handling and Logging</h1>
Error handling is implemented within the Groovy scripts to catch exceptions during PDF generation. If an error occurs, a message indicating the failure is set as the body of the message, and the content type is adjusted accordingly.

<h1 style="color: #1f4e79;">5. Testing Validation</h1>
Key testing scenarios include:
- Sending emails with various content types (plain text, HTML).
- Validating the generated PDFs for correct content.
- Ensuring that the emails are sent with the correct attachments.

<h1 style="color: #1f4e79;">6. Reference Documents</h1>
- iFlow Content: `Task1.iflw`
- Groovy Scripts: 
  - `script1.groovy`
  - `script3.groovy`
  - `script20.groovy`
  - `script30.groovy`
  - Additional scripts for PDF generation and attachment handling.
```
