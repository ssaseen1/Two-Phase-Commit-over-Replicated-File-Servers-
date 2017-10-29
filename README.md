# Two-Phase-Commit-over-Replicated-File-Servers-
Java Multithreaded &amp; SQLite

Implementation Description :

TwoPhaseProtocol Implementation:
The protocol consist of a single coordinator and several Participants. 
Method stubs are generated corresponding to the defined services by Thrift after processing the fileservice.thrift file.
writeFile given a name and file content, the corresponding file should be written to the Participants. Metainformation,
only include the filename which is stored at the Participant side. 
Each Participant maintains a Folder called Source which consist of all the files written to the Participant.
readFile, if a file with a given file name, returns the content of the file . Otherwise, an exception is trown as "File does not exist"
Both the Coordinator and the Participants maintains a Database with contains unique transaction_id, filename, filecontent, operation, and messages (abort, commit, global_abort, global_commit, vote_request).


How to compile :
===============

==> Coordinator:
Requirements to run the Coordinator:
participant_info.txt
1)make
2)bash
3)source source
4)chmod a+x Coordinator.sh
5)./Coordinator.sh <CoordinatorHostname> <CordinatorPortNumber>

Requirements to run the CoordinatorInvoker:
participant_info.txt
1)make
2)bash
3)source source
4)chmod a+x CoordinatorInvoker.sh
5)./CoordinatorInvoker.sh <CoordinatorHostname> <InvokerPortNumber> <CordinatorPortNumber>

==> Participant:
Requirements to run the Participant:
participant_info.txt
1)make
2)bash
3)source source
4)chmod a+x Coordinator.sh
5)./Coordinator.sh ParticipantHostname ParticipantPortNumber

Requirements to run the ParticipantInvoker:
p_info.txt
1)make
2)bash
3)source source
4)chmod a+x ParticipantInvoker.sh
5)./ParticipantInvoker.sh ParticipantHostname InvokerPortNumber ParticipantPortNumber

==> TwoPhaseProtocolInvoker:
Requirements to run the TwoPhaseProtocolInvoker:
client_info.txt
participant_info.txt
invoker_info.txt
1)make
2)bash
3)source source
4)chmod a+x TwoPhaseProtocolInvoker.sh
5)./TwoPhaseProtocolInvoker.sh TestCaseNumber

