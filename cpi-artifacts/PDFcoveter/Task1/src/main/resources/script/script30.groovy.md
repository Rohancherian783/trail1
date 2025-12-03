# Technical Documentation for Groovy Script

## 1. Purpose
The purpose of this Groovy script is to process incoming email messages by cleaning the email body, generating two PDF attachments from the cleaned content, and preparing the outgoing email with the generated PDFs attached. The script ensures that the email content is formatted correctly and that any unwanted text or formatting is removed before creating the PDFs.

## 2. Input/Output Headers
### Input Headers
- **CamelMailSubject**: The subject of the incoming email.
- **Subject**: An alternative header for the email subject.
- **mail_subject**: Another alternative header for the email subject.

### Output Headers
- **Subject**: The subject of the outgoing email, set to the cleaned incoming subject.
- **Attachments**: Two PDF files generated from the cleaned email body.

## 3. Code Summary and Logic
The script follows a structured approach to process the email:

1. **Email Body Retrieval**: The raw email body is retrieved as a string.
2. **Email Body Cleanup**:
   - Removes a disclaimer block at the end of the email using a regex pattern.
   - Strips all HTML tags and replaces them with a single space.
   - Replaces carriage returns with spaces while preserving newlines.
   - Removes specific unwanted header text patterns.
   - Consolidates multiple newlines into two for better formatting.
   - Reduces multiple spaces to two to maintain alignment in plain-text tables.
   - Trims leading and trailing whitespace.
3. **Subject Retrieval**: The script attempts to retrieve the email subject from various headers, defaulting to "EmailReport" if none are found.
4. **Logging**: Optionally logs the incoming subject and cleaned email body for debugging purposes.
5. **PDF Filename Preparation**: Cleans the subject for use as a PDF filename, ensuring it does not exceed 50 characters and does not contain invalid characters.
6. **PDF Creation**: A helper function creates a PDF DataHandler from the cleaned email body using the iText library.
7. **PDF Generation**: Two identical PDFs are created from the cleaned email body.
8. **Attachment Handling**: The generated PDFs are attached to the message with dynamic filenames.
9. **Final Email Body**: Sets the body of the outgoing email to inform the recipient about the attached PDFs.

## 4. Recommendations/Best Practices
- **Error Handling**: Implement error handling to manage potential exceptions during PDF creation or email processing. This will enhance the robustness of the script.
- **Logging**: Consider adding more detailed logging, especially for error scenarios, to facilitate troubleshooting.
- **Configuration**: Externalize configurable parameters (like the disclaimer pattern or unwanted text patterns) to make the script more adaptable to changes without modifying the code.
- **Testing**: Thoroughly test the script with various email formats to ensure that the cleanup logic works as intended and that the PDFs are generated correctly.
- **Performance**: If processing large volumes of emails, consider optimizing the regex patterns for performance, as complex regex can slow down execution.
- **Documentation**: Maintain inline comments and documentation to clarify the purpose of each section of the code, especially for complex regex patterns and logic.
