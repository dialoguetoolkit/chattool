# About

The toolkit is an instant-messaging platform for conducting research on dialogue. The software consists of a chat server and chat clients, written in Java.

The chat-tool makes data-collection and preparation much easier and quicker. Configuration of the server and clients allows experimental designs that are much more standardized and reproducible.
The toolkit can be used "off-the-shelf" to collect data from instant-messaging conversations. It is also a powerful, programmable toolkit for conducting experiments on live interaction.

All participants' turns are routed via the server which can be scripted to detect, for example target words, phrases or syntactic constructions. This information can then be used, in real-time to trigger experimental interventions that are sensitive to the interactional context.

The toolkit provides an extensive API for scripting these manipulations, as well as a constantly expanding library of experimental setups that can be reconfigured.

# Features

## New version

Some of the new features are:

- **Telegram** The chattool interfaces with the Telegram messenger service (https://telegram.org/). Participants can now use the Telegram messenger app on their mobile phone to connect to the chattool.
- **Remote admin** The experimenter can supervise experiments remotely from their mobile phone
- **Customizable referential tasks** It is now possible to build new referential tasks, without having to program any code
- **Scripting interventions** It is now possible to specify experiments which transform participants' turns, without the experimenter having to program any code
- **WYSIWYG Interface** The chattool includes a new interface character-by-character interface (What You See is What You Get) which is suitable for investigating incremental language processes
- **Linguistic communities** Participants can be dynamically (re)assigned to different groups, allowing the investigation of community topologies on linguistic conventions
- **Simultaneous interactions** The chat-tool can be used to conduct experiments with hundreds of groups participating simultaneously


## Recorded data

The chattool automatically records all keypresses, words and turns, notifications (including typing notifications and read receipts), number of edits, typing speed, typing overlap.

Instead of costly and time-intensive transcription, all data is immediately available for analysis. The data is saved in a variety of formats - to help both qualitative and quantitative analyses (e.g. loading into SPSS, R, Excel, MATLAB).


## Integrated tasks

The chat tool includes a set of dialogue tasks, including:

* The <a href =    "https://www.cambridge.org/core/journals/behavioral-and-brain-sciences/article/toward-a-mechanistic-psychology-of-dialogue/83442BA495E0D5F81BDB615E4109DBD2" >maze game </A>
* The <a href = "https://pdfs.semanticscholar.org/dd2b/dd2c4df589cc3be1f4bfab6c42d8a9dc6609.pdf">tangram task </A> (which can be adapted to most other joint reference tasks)
* The <a href = "http://pure.au.dk/portal/files/47690659/psychological_science_2012_fusaroli_0956797612436816.pdf"> confidence task </A>
<p></p> 

Other tasks can easily be programmed in java

<p></p>

## Manipulating the interface

Existing software (e.g. whatsapp, viber, line, wechat) use subtly different interfaces that have undocumented, ad-hoc and constantly changing features. The effect of these interface features on interaction are currently poorly understood.
The toolkit allows configuration of all aspects of the interface, including the screen dimensions, text (colour, font, positioning), typing synchrony and interleaving of turns, typing status, read receipts, as well as simulating network conditions, e.g. latency. 



## Manipulating the interaction

The real strength of this toolkit is in manipulating the interaction. Since all turns are relayed through the server, the server can identify and transform participants' contributions. Possible kinds of experimental intervention include: 

* **Conceptual & linguistic coordination**

  * Manipulation of lexical, syntactic and semantic constituents (e.g. by inserting "spoof" clarification requests into the  interaction that target a specific element - see image below).
  * Manipulation of the specificity of referring expressions.
  * Manipulation of signals of positive feedback (e.g. inserting, blocking or transforming signals such as "ok", "yeah")
  * Manipulation of signals of negative feedback (e.g. inserting, blocking or transforming clarification requests such as "what?" or "huh?" or "sorry why?")
  * Manipulation of discourse markers, e.g. "so?"
  * Manipulation of priming & levels of alignment.
  <p>
 
* **Procedural coordination**
  * Manipulation of the timing and sequencing of turns
  * Introducing artificial hesitations//disfluencies (e.g. "umm", "uhh")
  <p>
 
* **Group-membership and identity:**
  * Manipulation of participatory status (e.g overhearer vs. bystander).
  * Manipulation of (apparent) identity of participant (e.g. gender, age, community membership)
  * Assigning participants to different sub-groups, e.g. to allow group-specific conventions to emerge.
  <p>



# Running the toolkit


## To run the software:

- On windows:
  1. Download the <a href = "a href = "https://github.com/dialoguetoolkit/chattool/releases/download/v5.4.0/chattool.zip"> <b>latest version</b></a>  of the chattool 
  2. Unzip it
  3. Run **runme.bat**
  
- On mac / linux
  1. Download the <a href = "https://github.com/dialoguetoolkit/chattool/releases/download/v5.4.0/chattool.zip"> <b>latest version</b></a>  of the chattool
  2. Unzip it
  3. Double-click on "chattool.jar"
  4. If double-clicking doesn't work, use a terminal window to type **java -jar "chattool.jar"**
  5. If the terminal window doesn't work, you need to install java and then try steps 3 or 4 again!

 
# Documentation

There are two sets of documentation:

The <a href = "https://github.com/dialoguetoolkit/chattool/blob/master/docs/usermanual/usermanual.pdf">user manual</a> explains how to use the software.


The documentation for programming the chattool is at  <a href = "https://dialoguetoolkit.github.io/docs/">https://dialoguetoolkit.github.io/docs</a>



# Development 

The source code of the project is hosted at  <a href = "https://github.com/dialoguetoolkit/chattool">https://github.com/dialoguetoolkit/chattool</a>. 

# Contact

If you have any questions, requests, or would like to contribute - please email  g.mills@gold.ac.uk
   
See also: https://gjmills.github.io/


# Publications


<p> Amido, S., Maraev, V., & Howes, C. (2024). “No, you listen!” A pilot experiment into escalation devices in confrontational conversation. In Proceedings of the 28th Workshop on the Semantics and Pragmatics of Dialogue.</p>

<p> Mills, G., & Redeker, G. (2024).  <a href = "https://onlinelibrary.wiley.com/doi/full/10.1111/cogs.13329" > Self‐Repair Increases Referential Coordination </a>a>. Cognitive Science, 47(8), e13329.</p>

<p>Vanzan, V., Qiu, A. H., Khan, F. A., Soupiona, C., & Howes, C. (2024, September). Emoji-Text Mismatches: Stirring the Pot of Online Conversations. In Proceedings of the 28th Workshop on the Semantics and Pragmatics of Dialogue.</p>

<p>Giannitzi, E. (2024). When eyes meet laughter: exploring non-verbal cues in human-robot interaction with Furhat.</p>

<p>Decker, A., Amblard, M., & Breitholtz, E. (2023, November). Analysing topic shifts in task-oriented dialogues. In Journées scientifiques du GDR Lift-LIFT 2023.</p>

<p>Mills, G. J. and Healey, P. G. T. (submitted) <a href = "dialoguetoolkitsubmitted.pdf"><B>A dialogue experimentation toolkit.</B></A>
 
<p>Mills, G.,  Gregoromichelaki, E., Howes, C., Maraev, V. (2022) <a href = "https://github.com/gjmills/gjmills.github.io/raw/master/files/manipulatinglaughter_accepted_interaction_studies.pdf"><B>Influencing laughter with AI-mediated communication</B> </a> <I>Interaction Studies  </I><b><a href = "https://psyarxiv.com/ysf7v/">PsyArXiv </a></b></p>

<p>Nord, E. A., Maraev, V., Breitholtz, E., & Howes, C. (2021, September). What do you mean? Eliciting enthymemes in text-based dialogue. In Proceedings of the 25th Workshop on the Semantics and Pragmatics of Dialogue.</p>P>

<p>Goodkind, A. (2021). Using keystrokes to predict social dynamics in dialogue (Doctoral dissertation, PhD Dissertation, Northwestern University, 2023, https://adamgoodkind. com/files/Goodkind_Dissertation. pdf).</p>
 
 <p> Maraev, V., Mazzocconi, C., Mills, G. Howes, G. (2020)  <a href = "https://biecoll.ub.uni-bielefeld.de/index.php/lw2020/article/view/928">“LOL what?”: Empirical study of laughter in chat based dialogues</a>. Laughter and Other Non-Verbal Vocalisations Workshop 2020, Bielefeld.
 
<p>Nölle, J., Fusaroli, R., Mills, G., & Tylén, K. (2020). Language as shaped by the environment: linguistic construal in a collaborative spatial task.</p>
 
 
<p>Lelonkiewicz, J. and Gambi, C. 2020. Making oneself predictable in linguistic interactions. Acta Psychologica 209, article number: 103125. (10.1016/j.actpsy.2020.103125)</p
 
 
<p>Castillo, L., Smith, K., & Branigan, H. P. (2019). Interaction Promotes the Adaptation of Referential Conventions to the Communicative Context. Cognitive science, 43(8), e12780. </p>
 
 
 
 
 <p>Castillo, L., Branigan, H., & Smith, K. (2015). Context influence vs efficiency in establishing conventions: Communities do it better. SEMDIAL 2015 goDIAL, 162. </p>

<p>Castillo, L(2019) <A HREF="https://www.researchgate.net/profile/Lucia_Castillo2/publication/334966060_Interaction_Promotes_the_Adaptation_of_Referential_Conventions_to_the_Communicative_Context/links/5d5a756892851c376369814b/Interaction-Promotes-the-Adaptation-of-Referential-Conventions-to-the-Communicative-Context.pdf">Interaction Promotes the Adaptation of Referential Conventions to the Communicative Context (Phd Thesis)</A>

<p>Philalithis, E (2019) <A HREF = "https://www.era.lib.ed.ac.uk/bitstream/handle/1842/35831/Philalithis2019.pdf?sequence=1&isAllowed=y">The many Worlds of meaning: A framework for object reference. PhD Thesis. </A> </p>

<p>Atkinson, M., Mills, G. J., & Smith, K. (2018). <A HREF= "https://academic.oup.com/jole/advance-article/doi/10.1093/jole/lzy010/5146761"> Social group effects on the emergence of communicative conventions and language complexity.</A> Journal of Language Evolution.</p>


<p>Healey, P. G., Mills, G. J., Eshghi, A., & Howes, C. (2018). Running repairs: Coordinating meaning in dialogue. Topics in cognitive science, 10(2), 367-388.</p>   

<p> Gao, G., Hwang S. Y., Jung M., & Fussell, S.R. (2018). Beyond Information Content: The Effects of Culture on Affective Grounding in Instant Messaging Conversations. CSCW 2018.</p>


<p> Yu, Y., Eshghi, A., Mills, G., & Lemon, O. J. (2017). The burchak corpus: a challenge data set for interactive learning of visually grounded word meanings. In Belz, Anya, Erkut Erdem, Katerina Pastra, and Krystian Mikolajczyk. "Proceedings of the Sixth Workshop on Vision and Language." In Proceedings of the Sixth Workshop on Vision and Language. 2017.</P>

<p>Liebman, N., & Gergle, D. (2016, February).<b> It's (Not) Simply a Matter of Time: The Relationship Between CMC Cues and Interpersonal Affinity.</b> In Proceedings of the 19th ACM Conference on Computer-Supported Cooperative Work & Social Computing (pp. 570-581). ACM.</p>

<p>Concannon, S. (2017). <b>Taking a stance: experimenting with deliberation in dialogue </b>  PhD Thesis, Queen Mary, University of London. </p>

<p>Concannon, S., Healey, P. G., & Purver, M. (2015). <a href = "http://www.eecs.qmul.ac.uk/~mpurver/papers/concannon-et-al15semdialexp.pdf"><b>Shifting Opinions: An Experiment on Agreement and Disagreement in Dialogue</b></a> SEMDIAL 2015 goDIAL, 15.</p>
         
<p>Nolle, Jonas, Kristian Tylen, and Gregory Mills. (2015) "Environmental affordances shape linguistic coordination in the maze game." </p>
         
<p>Jacobi, J., de Rechteren, A., Mills, G., & Redeker, G. (2015). Dutch-speaking children’s co-ordination skills in dialogue. Annual TABU Dag (Taalbulletin), Groningen </p>
         
<p>Engbrenghof, M., Mills, G., Redeker, G. (2015). Manipulating evidence of grounding in a collaborative communication game. Annual TABU Dag (Taalbulletin), Groningen </p>
          
<p>Mills, G. J. (2014) <a href = "http://dx.doi.org/10.1016/j.newideapsych.2013.03.006"><B>Dialogue in joint activity: complementarity, convergence, conventionalization</B></A> New Ideas in Psychology.</p>

<P>Mills,  G. J.  (2012)<B> Clarifying reference and plans in dialogue</B> . Fifth International Conference of the German Cognitive Linguistics Association. University of Freiburg 10-12 October. </P>

<P>Christine Howes. (2012) <B><A HREF = "https://qmro.qmul.ac.uk/jspui/handle/123456789/2486"> Coordination in dialogue: Using compound contributions to join a party</A></B>. PhD thesis, Queen Mary University of London, 2012. </P> 
         
<P> Mills, G.J.  (2012) <B> Making and breaking procedural conventions in dialogue</B> . Poster presented at Annual meeting of the cognitive science society (CogSci), Kyoto</P>

<P> Davidenko, N. and Mills G. J.  (2012)<B> Describing faces: Conventionalizing ontologies through dialogic interaction</A></B> . Poster presented at Annual meeting of the cognitive science society (CogSci), Kyoto</P>

<P>Christine Howes, Patrick G. T. Healey, Matthew Purver, and Arash Eshghi. (2012) <B><A HREF= "http://www.eecs.qmul.ac.uk/~mpurver/papers/howes-et-al12amlap.pdf"> Finishing each other's. . . responding to incomplete contributions in dialogue</A></B>. In Architectures and Mechanisms for Language Processing, Riva del Garda, 2012. </P>

<P>Christine Howes, Patrick Healey and Matthew Purver (2012).  Whose turn is it anyway? Same- and cross-person compound contributions in dialogue. Poster, in CogSci 2012, Sapporo, August 2012. </P>

<P>Christine Howes, Patrick G. T. Healey, Matthew Purver, and Arash Eshghi. (2012)<B><A HREF = "http://mindmodeling.org/cogsci2012/papers/0094/paper0094.pdf"> Finishing each other's. . . responding to incomplete contributions in dialogue</A>.</B> In Proceedings of the 32nd Annual Conference of the Cognitive Science Society, Sapporo, 2012. </P>

<p>Mills, G. J. (2011) <a href = "https://citeseerx.ist.psu.edu/viewdoc/download?doi=10.1.1.208.5281&rep=rep1&type=pdf"><B>The emergence of procedural conventions in dialogue</B></A> In Proceedings of the 33rd Annual Conference of the Cognitive Science Society. Boston. USA</P>

<p>Howes, C. Purver, M., Healey, P. G.T., Mills, G. (2011)<B> <A HREF="http://cogsci.eecs.qmul.ac.uk/diet/publications/d&d2011a.pdf">   On Incrementality in Dialogue: Evidence from Compound Contributions</A></B> Dialogue and Discourse. Vol 2, No 1. Special Issue on Incremental Processing in Dialogue.</P>

<p>Gregoromichelaki, E., Kempson, R., Purver, M., Mills, G., Cann, R. (2011)<A HREF = "https://journals.linguisticsociety.org/elanguage/dad/article/view/363.html"> <B>Incrementality and intention-recognition in utterance processing </B></A>Vol 2, No 1. Special Issue on Incremental Processing in Dialogue.</p>

<p>Patrick G. T. Healey, Arash Eshghi, Christine Howes, and Matthew Purver.(2011) <B><A HREF ="http://www.eecs.qmul.ac.uk/~mpurver/papers/healey-et-al11std.pdf">Making a contribution: Processing clarification requests in dialogue</A></B>. In Proceedings of the 21st Annual Meeting of the Society for Text and Discourse, Poitiers, July 2011. </P>

<p>Mills, G. J. and Gregoromichelaki, E. (2010) <B> <A HREF = "https://elenigregor.github.io/files/MillsGregorFromProceed.pdf">Establishing coherence in dialogue: sequentiality, intentions and negotiation.</A></B> In Proceedings of SemDial (PozDial). Adam Mickiewicz University, Poznan, Poland 16-18th June. </p>

<p>Kempson, R., Gregoromichelaki E., Mills, G. Purver, M., Howes, C., Healey, P. (2010) <B>On Dialogue Modelling, Language Processing Dynamics, and Linguistic Knowledge</B> Linguistic Evidence 2010, Tübingen, Feb 2010. </P>

<p>Howes, C. , Healey, P. G. T., Mills, G. J. (2009) <B> A: an experimental investigation into B:...split utterances </B> In Proceedings of SIGDIAL 2009: the 10th Annual Meeting of the Special Interest Group in Discourse and Dialogue, Queen Mary University of London, Sept. 2009.

<p>Kempson R, Gregoromichelaki E, Purver M, Mills G, Gargett A, Howes C (2009) <B> How mechanistic can accounts of interaction be? </B> In: Proceedings of Diaholmia, the 13th workshop on the semantics and pragmatics of dialogue</p>

<p>Mills, G. J. and Healey, P.G.T. (2008) <B><A HREF = "https://www.aclweb.org/anthology/W08-0106.pdf">Semantic negotiation in dialogue: mechanisms of alignment.</A></B> in Proceedings of the 8th SIGdial workshop on Discourse and Dialogue, Columbus, OH, US; June 2008.</P>

<P>Mills, G. J. (2007) <B>Semantic co-ordination in dialogue: the role of direct interaction.</B>  PhD Thesis. </P>


<p>Mills, G.J. and Healey, P. G. T. (2006) <A HREF = "https://publishup.uni-potsdam.de/opus4-ubp/frontdoor/deliver/index/docId/923/file/mills_haley.pdf"><B>Clarifying Spatial Descriptions: Local and Global Effects on Semantic Co-ordination</B></A> , In Proceedings of the 10th Workshop on the Semantics and Pragmatics of Dialogue. Potsdam. Germany.</P>

<p>Healey, P.G.T. and Mills, G.J. (2006) <B><A HREF = "http://csjarchive.cogsci.rpi.edu/Proceedings/2006/docs/p1470.pdf">Participation, Precedence and Co-ordination in Dialogue.</A></B> In Proceedings of the 28th Annual Conference of the  Science Society. Vancouver. Canada.</p>

<P>Matthew Purver. The Theory and Use of Clarification Requests in Dialogue. PhD Thesis, University of London, 2004. </P>

<p>Healey, P.G.T., Purver, M., King, J., Ginzburg, J. and Mills, G. J. (2003) <B> Experimenting with Clarification in Dialogue</B>. in Alterman, R. and Kirsh, D. (eds) Proceedings of the 25th Annual Conference of the Cognitive Science Society. Mahwah, N. J.: LEA pp. 539-544.</p>


<p>Purver, M., Healey, P. G. T., King, J. Ginzburg, J. and Mills, G. (2003) <B>Answering Clarification Questions.</B> In Proceedings of the 4th SIGdial Workshop on Discourse and Dialogue, pp 23-33, Association for Computational Linguistics, Sapporo, Japan, July 2003.</P>

  
# Related toolkits 


* <B>Speech:</B> https://arxiv.org/pdf/1812.03415.pdf

* <B>Speech:</B> https://link.springer.com/article/10.3758/s13428-017-0873-y

* <B>Video:</B> https://pablo-arias.github.io/ducksoup.html 





