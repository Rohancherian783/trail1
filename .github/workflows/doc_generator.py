import os
import json
from docx import Document
from openai import OpenAI
import sys

# --- Configuration ---
# Read dynamic inputs from environment variables
INPUT_PACKAGE_NAME = os.environ.get("INPUT_PACKAGE_NAME")
INPUT_IFLOW_NAME = os.environ.get("INPUT_IFLOW_NAME")
GITHUB_WORKSPACE_ROOT = os.environ.get("GITHUB_WORKSPACE_ROOT") # New environment variable

# *** PATHS *** (Relative to the .github/workflows/ directory)
DOCX_TEMPLATE_PATH = '../../assets/project_template.docx' 

# Define output path components:
RELATIVE_OUTPUT_PATH_DIR = f'cpi-artifacts/{INPUT_PACKAGE_NAME}/{INPUT_IFLOW_NAME}'
OUTPUT_FILENAME = f'{INPUT_IFLOW_NAME}_Technical_Spec_Body.html'

# Project-specific context (Update static details here)
PROJECT_CONTEXT = {
    "iflow_name": INPUT_IFLOW_NAME,
    "package_name": INPUT_PACKAGE_NAME,
    "author": "Rohancherian783",
    "source_system": "SAP S/4HANA (IDoc)",
    "target_system": "Internal HR System (REST API)",
    "integration_scenario": "Asynchronous Employee Data Replication",
    "security_protocol": "Basic Authentication over HTTPS"
}
# --- Helper Functions (omitted for brevity, assume extract_text_from_docx and call_openai_api are correct) ---

def extract_text_from_docx(file_path):
    """Reads a .docx file and returns its content as a single string."""
    try:
        if not os.path.exists(file_path):
            print(f"ERROR: Word template not found at {file_path}")
            sys.exit(1)
        # ... (rest of docx reading logic)
        document = Document(file_path)
        full_text = [p.text for p in document.paragraphs if p.text.strip()]
        return '\n\n'.join(full_text)
    except Exception as e:
        print(f"ERROR: Failed to read DOCX file: {e}")
        sys.exit(1)

def call_openai_api(prompt, api_key):
    # ... (API calling logic remains the same) ...
    try:
        client = OpenAI(api_key=api_key)
        response = client.chat.completions.create(
            model="gpt-4o-mini",
            messages=[
                {"role": "system", "content": "You are an expert SAP CPI Technical Writer. Generate clean, valid HTML format."},
                {"role": "user", "content": prompt}
            ],
            temperature=0.3,
        )
        return response.choices[0].message.content
    except Exception as e:
        print(f"FATAL ERROR: OpenAI API call failed: {e}")
        return ""


def main():
    """Main function to generate and save the document."""
    
    # 1. Check for required environment variables
    if not INPUT_PACKAGE_NAME or not INPUT_IFLOW_NAME or not GITHUB_WORKSPACE_ROOT:
        print("FATAL ERROR: Required environment variables (inputs/workspace) are missing.")
        sys.exit(1)
        
    openai_api_key = os.environ.get("OPENAI_API_KEY")
    if not openai_api_key:
        print("FATAL ERROR: OPENAI_API_KEY environment variable not set.")
        sys.exit(1)

    # 2. Calculate ABSOLUTE paths for saving
    ABSOLUTE_OUTPUT_DIR = os.path.join(GITHUB_WORKSPACE_ROOT, RELATIVE_OUTPUT_PATH_DIR)
    ABSOLUTE_OUTPUT_FILE_PATH = os.path.join(ABSOLUTE_OUTPUT_DIR, OUTPUT_FILENAME)
    
    # 3. Extract Template Content
    template_content = extract_text_from_docx(DOCX_TEMPLATE_PATH)

    # 4. Generate Document Body (API call logic)
    context_str = json.dumps(PROJECT_CONTEXT, indent=2)
    prompt = f"Project Context:\n{context_str}\n\n--- TEMPLATE ---\n{template_content}" # Simplified prompt for brevity
    document_body_html = call_openai_api(prompt, openai_api_key)
    
    if not document_body_html.strip():
        print("ERROR: AI returned empty content. Failing the workflow.")
        sys.exit(1) 

    # 5. Assemble and Save the Final Document
    os.makedirs(ABSOLUTE_OUTPUT_DIR, exist_ok=True)
    
    # Save using the ABSOLUTE path
    with open(ABSOLUTE_OUTPUT_FILE_PATH, 'w', encoding='utf-8') as f:
        f.write(document_body_html)
        
    print(f"✅ Successfully generated and saved document body to {ABSOLUTE_OUTPUT_FILE_PATH}")

    # 6. --- CRITICAL FIX: Save the relative path for the Bash script ---
    # The path must be relative to the repository root.
    RELATIVE_PATH_TO_SAVE = os.path.join(RELATIVE_OUTPUT_PATH_DIR, OUTPUT_FILENAME)
    
    # This file is created inside the working directory (.github/workflows/)
    with open('output_path.txt', 'w') as f:
        f.write(RELATIVE_PATH_TO_SAVE)
    
    print(f"✅ Saved relative path to output_path.txt: {RELATIVE_PATH_TO_SAVE}")


if __name__ == "__main__":
    main()
