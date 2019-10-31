# Text Wrap Kata
The exercise in this kata is to wrap some text string that it will fit into a given maximum 
length. Example:

**Text**:

    "One fine day, in the middle of the night, Two dead men got up to fight. A blind man came to 
    see fair play, A dumb man came to shout hurray."

**Wrapped Text, length 10:**
 
    "One fine  "
    "day, in   "
    "the middle"
    "of the    "
    "night, Two"
    "dead men  "
    "got up to "
    "fight. A  "
    "blind man "
    "came to   "
    "see fair  "
    "play, A   "
    "dumb man  "
    "came to   "
    "shout     "
    "hurray.   "

If a word of this text is to long then it will be split and the upper part end with a minus sign.
Example:

**Text:**
    
    "HumblebundleTestText"
    
**Wrapped Text, length 5:**
    
    "Humb-"
    "lebu-"
    "ndle-"
    "Test-"
    "Text "

#Variant
In the variant we try to create some kind of full justification for the text. Whitespaces
can be added between words and punctuation and word. Example:

**Text**:

    "One fine day, in the middle of the night, Two dead men got up to fight. A blind man came to 
    see fair play, A dumb man came to shout hurray."

**Wrapped Text, lenght 15**:

    "One  fine  day,"
    "in  the  middle"
    "of  the  night,"
    "Two  dead  men "
    "got    up   to "
    "fight. A  blind"
    "man came to see"
    "fair   play,  A"
    "dumb  man  came"
    "to        shout"
    "hurray.        "
