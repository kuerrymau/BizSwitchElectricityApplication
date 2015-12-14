The GWT API is huge and has its nuances, and one needs a lot of time to go through it

Errors/Issues/Road blocks
-------------------------

1. GWT seem to not play nice with other libraries; (also see Main.qwt.xml) see errors below

[ERROR] Line 78: No source code is available for type java.net.Socket; did you forget to inherit a required module?
[ERROR] Line 91: No source code is available for type java.security.Security; did you forget to inherit a required module?
[ERROR] Line 91: com.sun.net.ssl.internal.ssl cannot be resolved to a type
[ERROR] Line 92: No source code is available for type javax.net.ssl.SSLSocketFactory; did you forget to inherit a required module?
[ERROR] Line 120: No source code is available for type java.text.SimpleDateFormat; did you forget to inherit a required module?
[ERROR] Line 121: The method format(String, String, Integer, int, int, String, String) is undefined for the type String
[ERROR] Line 135: No source code is available for type java.io.Writer; did you forget to inherit a required module?
[ERROR] Line 135: No source code is available for type java.io.OutputStreamWriter; did you forget to inherit a required module?
[ERROR] Line 150: No source code is available for type java.io.BufferedReader; did you forget to inherit a required module?
[ERROR] Line 150: No source code is available for type java.io.InputStreamReader; did you forget to inherit a required module?

2. GWT Does not like the diamond operator for generics; see comments in MainEntryPoint.java and below

[ERROR] Line 223: Type mismatch: cannot convert from ArrayList<?> to List<ElecTrans>
[ERROR] Line 223: Cannot instantiate the type ArrayList<?>
[ERROR] Line 223: Syntax error on token "<", ? expected after this token

3. Applying css not working for all browsers, if there was more time, would have moved the view(s) to jsp (or read more on iubuilder).

Outstanding items:-
-------------------

Need to read up on how manipulate the DOM to add more widgets.

Because of number 1 above, I was not able to successfully fetch token codes using my portal; only managed to get responses
via unit tests.