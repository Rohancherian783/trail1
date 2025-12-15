import os
import json
from docx import Document
from openai import OpenAI
import sys # For clean exiting

# --- Configuration ---
# Your Word template path
DOCX_TEMPLATE_PATH = 'assets/project_template.docx' 
# Output directory where the generated file will be saved
OUTPUT_DIR = 'cpi-artifacts/PDFcoveter/Task1/'
OUTPUT_FILENAME = 'Task1_Technical_Spec_Body.html'

# Project-specific context (Update this with your iFlow details)
PROJECT_CONTEXT = {
    "iflow_name": "Task1",
    "author": "Rohancherian783",
    "source_system": "SAP S/4HANA (IDoc)",
    "target_system": "Internal HR System (REST API)",
    "integration_scenario": "Asynchronous Employee Data Replication",
    "security_protocol": "Basic Authentication over HTTPS",
    "data_flow": "S4HANA -> CPI -> HR System"
}

# --- Helper Functions ---

def extract_text_from_docx(file_path):
    """Reads a .docx file and returns its content as a single string."""
    try:
        if not os.path.exists(file_path):
            print(f"ERROR: Word template not found at {file_path}")
            # Exit 1 will fail the GitHub Action run
            sys.exit(1)
            
        document = Document(file_path)
        # Extract text from paragraphs, skipping empty lines
        full_text = [p.text for p in document.paragraphs if p.text.strip()]
        
        # Join paragraphs with two newlines for better AI separation
        return '\n\n'.join(full_text)
        
    except Exception as e:
        print(f"ERROR: Failed to read DOCX file: {e}")
        sys.exit(1)

def call_openai_api(prompt, api_key):
    """
    Calls the OpenAI API to generate the document content.
    """
    print("--- DEBUG: Calling OpenAI API for content generation ---")
    
    try:
        # Initialize the OpenAI client
        # The key is automatically read from the OPENAI_API_KEY environment variable if not specified, 
        # but we explicitly pass it for clarity.
        client = OpenAI(api_key=api_key)
        
        model = "gpt-4o-mini" 

        response = client.chat.completions.create(
            model=model,
            messages=[
                {"role": "system", "content": 
                 "You are an expert SAP CPI Technical Writer. Your task is to generate the "
                 "complete technical specification document in **clean, valid HTML format** "
                 "(using h1, h2, h3, p, and table tags). Fill in the template based on the "
                 "provided project context. Do NOT include the cover page or Table of Contents (TOC), "
                 "only provide the body content starting from Section 1."},
                {"role": "user", "content": prompt}
            ],
            temperature=0.3, # Keeps the output focused and technical
        )
        
        ai_generated_content = response.choices[0].message.content
        
        print(f"DEBUG: AI response received (length: {len(ai_generated_content)})")
        return ai_generated_content
        
    except Exception as e:
        print(f"FATAL ERROR: OpenAI API call failed: {e}")
        # Return an empty string on failure
        return ""


def main():
    """Main function to generate and save the document."""
    
    # 1. Get API Key from environment
    openai_api_key = os.environ.get("OPENAI_API_KEY")
    if not openai_api_key:
        print("FATAL ERROR: OPENAI_API_KEY environment variable not set. Did you configure the GitHub Secret?")
        sys.exit(1)

    # 2. Extract Template Content
    print(f"Attempting to read template from: {DOCX_TEMPLATE_PATH}")
    template_content = extract_text_from_docx(DOCX_TEMPLATE_PATH)
    # The extract_text_from_docx function handles exiting if the file is not found

    # 3. Construct the Prompt
    context_str = json.dumps(PROJECT_CONTEXT, indent=2)
    prompt = (
        f"Project Context (use this to fill the template):\n{context_str}\n\n"
        "--- START OF TECHNICAL SPECIFICATION TEMPLATE ---\n"
        f"{template_content}\n"
        "--- END OF TEMPLATE ---\n\n"
        "Generate the complete technical specification for the project described above, "
        "using the template structure provided. **The output must be clean HTML (NO Markdown)** "
        "starting from the Introduction (Section 1). Ensure the output is complete and accurate."
    )
    
    # 4. Generate Document Body 
    document_body_html = call_openai_api(prompt, openai_api_key)
    
    if not document_body_html.strip():
        print("ERROR: AI returned empty content. Failing the workflow.")
        sys.exit(1) 

    # 5. Assemble and Save the Final Document
    # NOTE: You will need to integrate the Cover Page/TOC generation from your other script
    # For now, we save only the generated body into the specified output path.
    
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    output_path = os.path.join(OUTPUT_DIR, OUTPUT_FILENAME)
    
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(document_body_html)
        
    print(f"✅ Successfully generated and saved document body to {output_path}")

if __name__ == "__main__":
    main()
