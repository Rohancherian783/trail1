import os
import json
from docx import Document
from openai import OpenAI
import sys

# --- Configuration ---
# Read dynamic inputs from environment variables
INPUT_PACKAGE_NAME = os.environ.get("INPUT_PACKAGE_NAME")
INPUT_IFLOW_NAME = os.environ.get("INPUT_IFLOW_NAME")

# *** PATHS *** (Relative to the .github/workflows/ directory)
DOCX_TEMPLATE_PATH = '../assets/project_template.docx' 
OUTPUT_DIR = f'../cpi-artifacts/{INPUT_PACKAGE_NAME}/{INPUT_IFLOW_NAME}/'
OUTPUT_FILENAME = f'{INPUT_IFLOW_NAME}_Technical_Spec_Body.html'

# Project-specific context (Update static details here, dynamic parts use inputs)
PROJECT_CONTEXT = {
    "iflow_name": INPUT_IFLOW_NAME, # Dynamic
    "package_name": INPUT_PACKAGE_NAME, # Dynamic
    "author": "Rohancherian783",
    "source_system": "SAP S/4HANA (IDoc)",
    "target_system": "Internal HR System (REST API)",
    "integration_scenario": "Asynchronous Employee Data Replication",
    "security_protocol": "Basic Authentication over HTTPS"
}
# --- Helper Functions ---

def extract_text_from_docx(file_path):
    """Reads a .docx file and returns its content as a single string."""
    try:
        if not os.path.exists(file_path):
            print(f"ERROR: Word template not found at {file_path}")
            sys.exit(1)
            
        document = Document(file_path)
        full_text = [p.text for p in document.paragraphs if p.text.strip()]
        return '\n\n'.join(full_text)
        
    except Exception as e:
        print(f"ERROR: Failed to read DOCX file: {e}")
        sys.exit(1)

def call_openai_api(prompt, api_key):
    """Calls the OpenAI API to generate the document content."""
    # ... (function body remains the same as before) ...
    try:
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
            temperature=0.3,
        )
        ai_generated_content = response.choices[0].message.content
        print(f"DEBUG: AI response received (length: {len(ai_generated_content)})")
        return ai_generated_content
        
    except Exception as e:
        print(f"FATAL ERROR: OpenAI API call failed: {e}")
        return ""


def main():
    """Main function to generate and save the document."""
    
    # 1. Check for inputs and API key
    if not INPUT_PACKAGE_NAME or not INPUT_IFLOW_NAME:
        print("FATAL ERROR: Package Name and iFlow Name were not passed from the workflow input.")
        sys.exit(1)
        
    openai_api_key = os.environ.get("OPENAI_API_KEY")
    if not openai_api_key:
        print("FATAL ERROR: OPENAI_API_KEY environment variable not set.")
        sys.exit(1)

    # 2. Extract Template Content
    print(f"Attempting to read template from: {DOCX_TEMPLATE_PATH}")
    template_content = extract_text_from_docx(DOCX_TEMPLATE_PATH)

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
    os.makedirs(OUTPUT_DIR, exist_ok=True)
    output_path = os.path.join(OUTPUT_DIR, OUTPUT_FILENAME)
    
    with open(output_path, 'w', encoding='utf-8') as f:
        f.write(document_body_html)
        
    print(f"✅ Successfully generated and saved document body to {output_path}")

if __name__ == "__main__":
    main()
