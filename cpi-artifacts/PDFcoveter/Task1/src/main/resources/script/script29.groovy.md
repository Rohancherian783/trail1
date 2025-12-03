# Technical Documentation for Groovy Script

## 1. Purpose
The purpose of this Groovy script is to process an incoming email message, extract its body content, and generate two PDF attachments from that content. These PDFs are then attached to the outgoing message, allowing the recipient to receive the email along with the generated reports.

## 2. Input/Output Headers
### Input Headers
- **Message**: The input message is expected to contain the body of an email in a string format.

### Output Headers
- **Message**: The output message will include the original email content along with two PDF attachments named `EmailReport_1.pdf` and `EmailReport_2.pdf`.

## 3. Code Summary and Logic
The script follows these main steps:

1. **Extract Email Body**: 
   - The script retrieves the body of the incoming email message using `message.getBody(String)`.

2. **PDF Creation Function**: 
   - A closure named `createPDF` is defined, which takes a string `content` as input. 
   - Inside this closure:
     - A new PDF document is created using the iText library.
     - A `ByteArrayOutputStream` is used to capture the PDF output.
     - The document is opened, the content is added as a paragraph, and then the document is closed.
     - The byte array of the PDF is returned.

3. **Generate PDFs**: 
   - The script calls the `createPDF` function twice with the email body content to generate two separate PDF files.

4. **Set Attachments**: 
   - A map named `attachments` is created to hold the generated PDFs.
   - The two PDFs are added to this map with their respective filenames.
   - The attachments are set on the message using `message.setAttachments(attachments)`.

5. **Return Message**: 
   - Finally, the modified message with the attachments is returned.

## 4. Recommendations/Best Practices
- **Error Handling**: Implement error handling to manage potential exceptions during PDF creation or message processing. This can prevent the script from failing silently.
  
- **Logging**: Consider adding logging statements to track the flow of data and any issues that may arise during execution. This can be helpful for debugging.

- **Dynamic Filenames**: Instead of hardcoding the PDF filenames, consider generating dynamic names based on timestamps or unique identifiers to avoid overwriting files.

- **Content Validation**: Before generating PDFs, validate the email body content to ensure it is not empty or null. This can prevent the creation of empty PDF files.

- **Performance Optimization**: If the email body is large, consider optimizing the PDF generation process to handle larger content efficiently.

- **Library Versioning**: Ensure that the iText library version used is compatible with the CPI environment and is kept up to date to leverage improvements and security fixes.
