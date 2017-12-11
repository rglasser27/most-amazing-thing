In Search of the Most Amazing Thing

I'm working on an Android port of a classic game from 1983. You can see more information here:
https://en.wikipedia.org/wiki/In_Search_of_the_Most_Amazing_Thing

I don't have a handle on the copyright situation with the original game, so if you are the owner,
please contact russell@russellglasser.com.

# Development notes

 The game was retrieved from [My Abandonware](https://www.myabandonware.com/game/in-search-of-the-most-amazing-thing-2c).
 
## Reading the code as text 
* The original .bas files from the game cannot be read in an ordinary text editor. However, if you load
"Basica" (included in the game files) you can load the source of any file and then resave it as ASCII.
    
        load "intro.bas"
        save "intro.txt",a

## Reference manual for Basic code

This [GWBasic manual](https://hwiegman.home.xs4all.nl/gw-man/index.html) is a fairly reliable reference for the
program code

## Decoding .crn images

The image files are bits that are written directly to the screen buffer, and then compressed using something called
"crunch." Crunch.bas and Decrunch.bas are used to generate the files, but they are written in assembly language, so
not readable in an editor.

Instead of trying to rewrite the method for decompressing the files, I simply used Decrunch.bas to decompress the files,
displayed them on the screen, and then used "bsave" to save them again uncompressed. You can find the decompressed
files in the res/raw folder as *.bsave. 

The resulting files are written as four bits per pixel, with some bit offsets, and the colors map to black, cyan,
magenta, and white. They are also saved in alternate scan lines, so all the even numbered lines are displayed first,
then the odd numbered lines. 

# Other useful links

* Some [Crunch file format decoders](https://github.com/HearthSim/decrunch/blob/master/crunch/crn_decomp.h)
written in C. I couldn't make it work, but it may be a handy link anyway. 
* A website for [reading a hex dump of files](http://www.fileformat.info/tool/hexdump.htm). Came in handy for
reading through the decompressed bitmaps to understand what was being displayed.
* [BSAVE/BLOAD File Format Explained for BASIC](https://www.pcjs.org/pubs/pc/reference/microsoft/kb/Q34407/). Doesn't
seem *quite* correct for these files, but pretty close.