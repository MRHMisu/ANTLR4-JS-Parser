"""

"""

import sys;
from xml.etree import ElementTree as ET


def extract_code_snippets(so_post_xml_file_path, language_tag, accepted_answer_xml_output):
    xml_parser = ET.iterparse(so_post_xml_file_path)
    accepted_answers_ids = set()
    accepted_answers_code = set()

    # Example
    # <row Id="4" PostTypeId="1" AcceptedAnswerId="7" Tags="&lt;discussion&gt;" AnswerCount="2" CommentCount="0" />
    # <row Id="7" PostTypeId="2" ParentId="4" />
    # PostTypeId 1 represents question, 2 represents answer

    for rc, (event, element) in enumerate(xml_parser):
        if element.tag == "row":
            if element.get("PostTypeId") == "1":  # explore the question
                if element.get("AcceptedAnswerId") and language_tag in element.get("Tags"):
                    accepted_answers_ids.add(element.get("AcceptedAnswerId"))
            elif element.get("PostTypeId") == "2":  # explore the answer
                answer_id = element.get("Id")
                if answer_id in accepted_answers_ids:
                    accepted_answers_ids.remove(answer_id)
                    # print("XXXXXXXXXXXXXXXXXXXXXX-answer-start-XXXXXXXXXXXXXXXXXXXX")
                    # print(element.get("Body"))
                    # print("XXXXXXXXXXXXXXXXXXXXXX-answer-end-XXXXXXXXXXXXXXXXXXXX")
                    accepted_answer = "<?xml version=\"1.0\"?>" + '\n' + "<answer>" + element.get(
                        "Body") + "</answer>" + '\n'
                    try:
                        root = ET.fromstring(accepted_answer)
                        # print("-------------codes start------------------------")
                        for sinnipet in root.iter('code'):
                            accepted_answers_code.add("<code>"+sinnipet.text.strip()+"</code>"+'\n')
                            # print(sinnipet.text)
                        # print("-------------codes End------------------------")
                    except ET.ParseError:
                        continue

        element.clear()
    write_answer_tag_list(accepted_answer_xml_output,accepted_answers_code)


def write_answer_tag_list(file_path, answers_tag_list):
    file_writer = open(file_path, 'a')
    file_writer.writelines((answers_tag_list))
    file_writer.close()


def write_xml_tag(file_path, tag):
    file_writer = open(file_path, 'a')
    file_writer.writelines(tag)
    file_writer.close()


if __name__ == "__main__":
    so_post_xml_file_path = sys.argv[1]
    language_tag = sys.argv[2]
    accepted_answer_xml_output = sys.argv[3]
    print(language_tag)
    write_xml_tag(accepted_answer_xml_output, "<?xml version=\"1.0\"?>" + '\n' + "<code_snippets>" + '\n')
    extract_code_snippets(so_post_xml_file_path, language_tag, accepted_answer_xml_output)
    write_xml_tag(accepted_answer_xml_output, "</code_snippets>")
    sys.exit()
