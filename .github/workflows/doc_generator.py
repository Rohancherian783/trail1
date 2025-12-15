import os
import json
from docx import Document
from openai import OpenAI
import sys
import xml.etree.ElementTree as ET # <<< ADDED IMPORT FOR XML PARSING

# --- Configuration ---
# Read dynamic inputs from environment variables
INPUT_PACKAGE_NAME = os.environ.get("INPUT_PACKAGE_NAME")
INPUT_IFLOW_NAME = os.environ.get("INPUT_IFLOW_NAME")
GITHUB_WORKSPACE_ROOT = os.environ.get("GITHUB_WORKSPACE_ROOT") 
openai_api_key = os.environ.get("OPENAI_API_KEY")

# --- PATHS ---
DOCX_TEMPLATE_PATH = '../../assets/project_template.docx' 

# Define output path components:
RELATIVE_OUTPUT_PATH_DIR = f'cpi-artifacts/{INPUT_PACKAGE_NAME}/{INPUT_IFLOW_NAME}'
OUTPUT_FILENAME = f'{INPUT_IFLOW_NAME}_Technical_Spec_Body.html'

# *** CRITICAL PATH FIX: Path to the .iflw file ***
IFLOW_SOURCE_FILENAME = f'{INPUT_IFLOW_NAME}.iflw' 
NESTED_IFLOW_SUBPATH = f'src/main/resources/scenarioflows/integrationflow/{IFLOW_SOURCE_FILENAME}'
RELATIVE_IFLOW_SOURCE_PATH = os.path.join(RELATIVE_OUTPUT_PATH_DIR, NESTED_IFLOW_SUBPATH)

# Project-specific context (Remains high-level)
PROJECT_CONTEXT = {
    "iflow_name": INPUT_IFLOW_NAME,
    "package_name": INPUT_PACKAGE_NAME,
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
    try:
        client = OpenAI(api_key=api_key)
        model = "gpt-4o-mini" 

        response = client.chat.completions.create(
            model=model,
            messages=[
                {"role": "system", "content": 
                 "You are an expert SAP CPI Technical Writer. Your task is to generate the "
                 "complete technical specification document in **clean, valid HTML format**. "
                 "Analyze the provided iFlow configuration data and fill in the document "
                 "with specific details on Endpoints, Mappings, Security, and Error Handling. "
                 "DO NOT include the cover page or Table of Contents (TOC)."},
                {"role": "user", "content": prompt}
            ],
            temperature=0.3,
        )
        ai_generated_content = response.choices[0].message.content
        return ai_generated_content
        
    except Exception as e:
        print(f"FATAL ERROR: OpenAI API call failed: {e}")
        return ""

def parse_iflow_xml(xml_content):
    """
    Parses the iFlow XML content to extract key technical details for the AI.
    *** CRITICAL: You MUST inspect your .iflw file and adjust these XPaths. ***
    """
    try:
        root = ET.fromstring(xml_content)
        details = {}
        
        # --- Example XPaths (Adjust these to match your CPI XML structure) ---
        # 1. Receiver Endpoint (Target URL)
        # Often found in a configuration property of a Send step
        receiver_address = root.find(".//endpoint/address") 
        details['Target_Endpoint_URL'] = receiver_address.text if receiver_address is not None and receiver_address.text else "N/A"
        
        # 2. Sender Details (Initial URL or Message Type)
        sender_type = root.find(".//sender/messageProtocol")
        details['Sender_Protocol'] = sender_type.text if sender_type is not None else "HTTPS/IDoc"

        # 3. Security (Find credential name or security method)
        credential = root.find(".//security/credentialName")
        details['Security_Credential_Name'] = credential.text if credential is not None else "BasicAuth_Default"
        
        # 4. Scripting Files (Custom logic)
        scripts = [s.get('name') for s in root.findall(".//script/resource")]
        details['Scripts_In_iFlow'] = ", ".join(scripts) if scripts else "None"

        # 5. Mapping File (If a Message Mapping is used)
        mapping = root.find(".//messageMapping/resource")
        details['Mapping_Resource'] = mapping.get('name') if mapping is not None else "None (Groovy/XSLT only)"

        return "\n".join([f"{k}: {v}" for k, v in details.items()])

    except ET.ParseError as e:
        print(f"ERROR: Failed to parse iFlow XML. Check if the file is valid XML: {e}")
        return f"ERROR: Could not parse iFlow XML. Detail: {e}"
    except Exception as e:
        print(f"ERROR during custom XML analysis: {e}")
        return f"ERROR: Failed during detail extraction. Detail: {e}"


def read_iflow_source_data(file_path):
    """Reads the raw iFlow XML content and returns it for parsing."""
    absolute_path = os.path.join(GITHUB_WORKSPACE_ROOT, file_path)
    try:
        with open(absolute_path, 'r', encoding='utf-8') as f:
            return f.read()
            
    except Exception as e:
        print(f"FATAL ERROR: Could not read iFlow XML file at {absolute_path}. Check path and permissions: {e}")
        sys.exit(1)


def main():
    """Main function to generate and save the document."""
    
    # 1. Check for required environment variables
    if not INPUT_PACKAGE_NAME or not INPUT_IFLOW_NAME or not GITHUB_WORKSPACE_ROOT or not openai_api_key:
        print("FATAL ERROR: Required environment variables (inputs/workspace/API Key) are missing.")
        sys.exit(1)

    # 2. Read Template Content and Raw XML
    template_content = extract_text_from_docx(DOCX_TEMPLATE_PATH)
    raw_iflow_xml = read_iflow_source_data(RELATIVE_IFLOW_SOURCE_PATH)

    # 3. --- CRITICAL NEW STEP: PARSE THE XML ---
    print(f"DEBUG: Attempting to parse XML for analysis...")
    INTEGRATION_SOURCE_DATA = parse_iflow_xml(raw_iflow_xml)

    # 4. Construct the Prompt (Uses the dynamically loaded content)
    context_str = json.dumps(PROJECT_CONTEXT, indent=2)
    prompt = (
        "You are an expert SAP CPI Technical Writer. Your task is to generate the "
        "document body in **clean HTML** format. **Use the ANALYZED DATA provided below** "
        "to fill in the specifics for the data flow, security, and error handling sections.\n\n"
        
        "--- HIGH-LEVEL CONTEXT ---\n{context_str}\n\n"
        
        "--- IF LOW ANALYZED DATA (USE THIS CLEAN SUMMARY) ---\n{INTEGRATION_SOURCE_DATA}\n\n"
        
        "--- DOCUMENT TEMPLATE TEXT ---\n{template_content}\n\n"
        
        "Generate the complete document body starting from **1. Introduction**."
    )
    
    # 5. Generate Document Body 
    document_body_html = call_openai_api(prompt, openai_api_key)
    
    if not document_body_html.strip():
        print("ERROR: AI returned empty content. Failing the workflow.")
        sys.exit(1) 

    # 6. Assemble and Save the Final Document
    ABSOLUTE_OUTPUT_DIR = os.path.join(GITHUB_WORKSPACE_ROOT, RELATIVE_OUTPUT_PATH_DIR)
    ABSOLUTE_OUTPUT_FILE_PATH = os.path.join(ABSOLUTE_OUTPUT_DIR, OUTPUT_FILENAME)
    
    os.makedirs(ABSOLUTE_OUTPUT_DIR, exist_ok=True)
    with open(ABSOLUTE_OUTPUT_FILE_PATH, 'w', encoding='utf-8') as f:
        f.write(document_body_html)
        
    print(f"✅ Successfully generated and saved document body to {ABSOLUTE_OUTPUT_FILE_PATH}")

    # 7. Save the relative path for the Bash script
    RELATIVE_PATH_TO_SAVE = os.path.join(RELATIVE_OUTPUT_PATH_DIR, OUTPUT_FILENAME)
    with open('output_path.txt', 'w') as f:
        f.write(RELATIVE_PATH_TO_SAVE)
    
    print(f"✅ Saved relative path to output_path.txt: {RELATIVE_PATH_TO_SAVE}")


if __name__ == "__main__":
    main()
