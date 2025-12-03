# Technical Documentation for Groovy Script

## 1. Purpose
The purpose of this Groovy script is to process an incoming message, generate two PDF documents based on the content of the message, and then create a MIME multipart response that includes the generated PDFs as attachments. The script ensures that if the message body is empty or null, a default message is used instead.

## 2. Input/Output Headers

### Input Headers
- **Message Body**: The script expects the body of the incoming message to be a string that contains the content to be included in the PDFs.

### Output Headers
- **Content-Type**: `multipart/mixed; boundary="----=_Part_0_123456789"`
- **Message Body**: A MIME formatted string that includes:
  - A text part with a message indicating that PDFs are attached.
  - Two PDF attachments encoded in Base64.

## 3. Code Summary and Logic

### Code Breakdown
1. **Imports**: The script imports necessary classes from the iText library for PDF generation and the SAP Gateway message handling utilities.
   
2. **Message Processing**:
   - The `processData` function is defined, which takes a `Message` object as input.
   - The body of the message is retrieved and checked for null or empty content. If it is empty, a default message "No content found." is assigned.

3. **PDF Generation**:
   - Two separate PDF documents are created using `iText`:
     - **First PDF**: Contains the text "First PDF:" followed by the email body.
     - **Second PDF**: Contains the text "Second PDF:" followed by the email body.
   - Both PDFs are written to `ByteArrayOutputStream` objects (`baos1` and `baos2`).

4. **MIME Multipart Creation**:
   - A MIME multipart message is constructed manually:
     - A boundary string is defined for separating parts.
     - A text part is added that informs the recipient about the attached PDFs.
     - Each PDF is added as a separate part, with appropriate headers for content type, encoding, and disposition.
     - The PDFs are encoded in Base64 format for safe transmission.

5. **Finalizing the Message**:
   - The constructed MIME string is set as the body of the message.
   - The content type header is set to indicate that the message is multipart.

6. **Return**: The modified message is returned.

## 4. Recommendations/Best Practices
- **Error Handling**: Implement error handling to manage exceptions that may arise during PDF generation or message processing. This can prevent the script from failing silently.
  
- **Dynamic Boundary Generation**: Instead of using a hardcoded boundary string, consider generating a unique boundary for each message to avoid potential conflicts.

- **Logging**: Add logging statements to track the flow of data and any issues that may arise during execution. This can be helpful for debugging.

- **Code Modularity**: Consider breaking down the script into smaller functions for better readability and maintainability. For example, separate functions for PDF generation and MIME construction could enhance clarity.

- **Resource Management**: Ensure that resources such as `ByteArrayOutputStream` and `Document` are properly closed in a `finally` block or use try-with-resources to manage them automatically.

- **Security Considerations**: When handling user-generated content, ensure that the input is sanitized to prevent injection attacks or other security vulnerabilities.
