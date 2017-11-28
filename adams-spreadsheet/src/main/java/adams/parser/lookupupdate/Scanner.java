/* The following code was generated by JFlex 1.4.3 on 29/11/17 12:37 PM */

/*
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


/*
 * Scanner.java
 * Copyright (C) 2016 University of Waikato, Hamilton, New Zealand
 */

package adams.parser.lookupupdate;

import java_cup.runtime.SymbolFactory;

import java.io.InputStream;

/**
 * A scanner for lookup update rules.
 *
 * @author FracPete (fracpete at waikato dot ac dot nz)
 * @version $Revision$
 */

public class Scanner implements java_cup.runtime.Scanner {

  /** This character denotes the end of file */
  public static final int YYEOF = -1;

  /** initial size of the lookahead buffer */
  private static final int ZZ_BUFFERSIZE = 16384;

  /** lexical states */
  public static final int STRING = 2;
  public static final int YYINITIAL = 0;

  /**
   * ZZ_LEXSTATE[l] is the state in the DFA for the lexical state l
   * ZZ_LEXSTATE[l+1] is the state in the DFA for the lexical state l
   *                  at the beginning of a line
   * l is of the form l = 2*k, k a non negative integer
   */
  private static final int ZZ_LEXSTATE[] = { 
     0,  0,  1, 1
  };

  /** 
   * Translates characters to character classes
   */
  private static final String ZZ_CMAP_PACKED = 
    "\11\0\1\54\1\53\1\0\1\54\1\61\22\0\1\54\1\46\1\57"+
    "\2\0\1\23\1\0\1\52\1\55\1\56\1\20\1\17\1\15\1\16"+
    "\1\50\1\21\1\34\1\33\1\37\7\47\1\12\1\14\1\44\1\13"+
    "\1\45\2\0\1\24\1\25\1\30\1\11\1\5\1\2\1\32\1\4"+
    "\1\1\2\51\1\7\1\42\1\6\1\31\1\36\1\26\1\27\1\10"+
    "\1\3\1\41\1\51\1\43\1\35\1\40\1\51\1\0\1\60\1\0"+
    "\1\22\1\51\1\0\1\24\1\25\1\30\1\11\1\5\1\2\1\32"+
    "\1\4\1\1\2\51\1\7\1\42\1\6\1\31\1\36\1\26\1\27"+
    "\1\10\1\3\1\41\1\51\1\43\1\35\1\40\1\51\uff85\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\11\2\1\1\1\3\1\4\1\5\1\6"+
    "\1\7\1\10\1\11\1\12\1\13\4\2\1\14\2\2"+
    "\1\15\1\16\1\1\1\2\1\1\1\17\1\20\1\21"+
    "\1\22\1\23\1\24\1\25\1\26\15\2\1\27\1\30"+
    "\10\2\1\31\4\2\1\32\1\33\1\34\1\14\1\0"+
    "\1\35\1\36\1\37\1\40\3\2\1\41\2\2\1\42"+
    "\1\2\1\43\1\44\1\45\1\46\3\2\1\47\1\50"+
    "\1\51\3\2\1\52\1\2\1\14\1\53\1\54\1\55"+
    "\1\56\2\2\1\57\1\60\1\61\1\2\1\62\1\2"+
    "\1\63\1\2\1\64\1\65\1\66\1\67\1\70\1\71"+
    "\1\2\1\72\1\73\1\74\1\75\1\2\1\76\1\53"+
    "\1\77";

  private static int [] zzUnpackAction() {
    int [] result = new int[132];
    int offset = 0;
    offset = zzUnpackAction(ZZ_ACTION_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAction(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /** 
   * Translates a state to a row index in the transition table
   */
  private static final int [] ZZ_ROWMAP = zzUnpackRowMap();

  private static final String ZZ_ROWMAP_PACKED_0 =
    "\0\0\0\62\0\144\0\226\0\310\0\372\0\u012c\0\u015e"+
    "\0\u0190\0\u01c2\0\u01f4\0\u0226\0\u0258\0\144\0\144\0\144"+
    "\0\u028a\0\144\0\144\0\u02bc\0\144\0\144\0\u02ee\0\u0320"+
    "\0\u0352\0\u0384\0\u03b6\0\u03e8\0\u041a\0\u044c\0\u047e\0\u04b0"+
    "\0\u04e2\0\u0514\0\144\0\144\0\144\0\144\0\u0546\0\144"+
    "\0\u0578\0\u0226\0\u05aa\0\u05dc\0\u060e\0\u0640\0\u0672\0\u06a4"+
    "\0\u06d6\0\u0708\0\u073a\0\u076c\0\u079e\0\u07d0\0\u0802\0\144"+
    "\0\u0834\0\u0866\0\u0898\0\u08ca\0\u08fc\0\u092e\0\u0960\0\u0992"+
    "\0\u09c4\0\u0226\0\u09f6\0\u0a28\0\u0a5a\0\u0a8c\0\144\0\144"+
    "\0\144\0\u0abe\0\u0af0\0\144\0\144\0\144\0\144\0\u0b22"+
    "\0\u0b54\0\u0b86\0\u0bb8\0\u0bea\0\u0c1c\0\u0226\0\u0c4e\0\u0226"+
    "\0\u0226\0\u0c80\0\u0cb2\0\u0ce4\0\u0d16\0\u0d48\0\u0226\0\u0226"+
    "\0\u0226\0\u0d7a\0\u0dac\0\u0dde\0\u0e10\0\u0e42\0\u0e74\0\u0ea6"+
    "\0\u0226\0\u0226\0\144\0\u0ed8\0\u0f0a\0\u0226\0\u0226\0\u0226"+
    "\0\u0f3c\0\u0226\0\u0f6e\0\u0226\0\u0fa0\0\u0226\0\u0fd2\0\u0226"+
    "\0\u0226\0\u0226\0\u0226\0\u1004\0\u0226\0\u0226\0\u0226\0\u0226"+
    "\0\u1036\0\u0226\0\u0226\0\u0226";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[132];
    int offset = 0;
    offset = zzUnpackRowMap(ZZ_ROWMAP_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackRowMap(String packed, int offset, int [] result) {
    int i = 0;  /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int high = packed.charAt(i++) << 16;
      result[j++] = high | packed.charAt(i++);
    }
    return j;
  }

  /** 
   * The transition table of the DFA
   */
  private static final int [] ZZ_TRANS = zzUnpackTrans();

  private static final String ZZ_TRANS_PACKED_0 =
    "\1\3\1\4\1\5\1\6\1\7\1\10\1\11\1\12"+
    "\1\13\1\14\1\15\1\16\1\17\1\20\1\21\1\22"+
    "\1\23\1\24\1\25\1\26\1\27\2\14\1\30\1\31"+
    "\1\32\1\14\2\33\1\14\1\34\1\33\2\14\1\35"+
    "\1\14\1\36\1\37\1\40\1\33\1\41\1\14\1\42"+
    "\2\43\1\44\1\45\1\46\1\3\1\43\53\47\1\0"+
    "\3\47\1\50\1\51\1\3\63\0\1\14\1\52\7\14"+
    "\4\0\1\14\5\0\20\14\3\0\3\14\11\0\6\14"+
    "\1\53\2\14\4\0\1\14\5\0\1\54\17\14\3\0"+
    "\3\14\11\0\3\14\1\55\5\14\4\0\1\14\5\0"+
    "\1\56\2\14\1\57\14\14\3\0\3\14\11\0\11\14"+
    "\4\0\1\14\5\0\14\14\1\60\3\14\3\0\3\14"+
    "\11\0\5\14\1\61\1\62\2\14\4\0\1\14\5\0"+
    "\11\14\1\63\6\14\3\0\3\14\11\0\11\14\4\0"+
    "\1\14\5\0\5\14\1\64\12\14\3\0\3\14\11\0"+
    "\11\14\4\0\1\14\5\0\5\14\1\65\12\14\3\0"+
    "\3\14\11\0\1\66\10\14\4\0\1\14\5\0\2\14"+
    "\1\67\15\14\3\0\3\14\11\0\11\14\4\0\1\14"+
    "\5\0\20\14\3\0\3\14\23\0\1\70\47\0\11\14"+
    "\4\0\1\14\5\0\7\14\2\33\2\14\1\33\4\14"+
    "\3\0\1\33\1\41\1\14\31\0\1\71\41\0\2\14"+
    "\1\72\2\14\1\73\1\74\2\14\4\0\1\14\5\0"+
    "\1\14\1\75\16\14\3\0\3\14\11\0\1\76\10\14"+
    "\4\0\1\14\5\0\20\14\3\0\3\14\11\0\4\14"+
    "\1\77\4\14\4\0\1\14\5\0\1\14\1\100\3\14"+
    "\1\101\12\14\3\0\3\14\11\0\11\14\4\0\1\14"+
    "\5\0\3\14\1\102\14\14\3\0\3\14\11\0\4\14"+
    "\1\103\4\14\4\0\1\14\5\0\7\14\2\33\2\14"+
    "\1\33\4\14\3\0\1\33\1\41\1\14\11\0\11\14"+
    "\4\0\1\14\5\0\5\14\1\104\12\14\3\0\3\14"+
    "\11\0\1\105\10\14\4\0\1\14\5\0\1\106\17\14"+
    "\3\0\3\14\23\0\1\107\31\0\1\110\27\0\1\111"+
    "\61\0\1\110\47\0\11\14\4\0\1\14\5\0\7\14"+
    "\2\112\2\14\1\112\4\14\3\0\1\112\2\14\10\0"+
    "\52\113\1\0\7\113\53\47\1\0\3\47\6\0\1\114"+
    "\2\0\1\115\20\0\1\116\27\0\1\117\3\0\11\14"+
    "\4\0\1\14\5\0\5\14\1\120\12\14\3\0\3\14"+
    "\11\0\6\14\1\121\2\14\4\0\1\14\5\0\20\14"+
    "\3\0\3\14\11\0\4\14\1\122\4\14\4\0\1\14"+
    "\5\0\20\14\3\0\3\14\11\0\5\14\1\123\3\14"+
    "\4\0\1\14\5\0\20\14\3\0\3\14\11\0\11\14"+
    "\4\0\1\14\5\0\15\14\1\124\2\14\3\0\3\14"+
    "\11\0\11\14\4\0\1\14\5\0\12\14\1\125\5\14"+
    "\3\0\3\14\11\0\10\14\1\126\4\0\1\14\5\0"+
    "\20\14\3\0\3\14\11\0\7\14\1\127\1\14\4\0"+
    "\1\14\5\0\20\14\3\0\3\14\11\0\11\14\4\0"+
    "\1\14\5\0\12\14\1\130\5\14\3\0\3\14\11\0"+
    "\2\14\1\131\6\14\4\0\1\14\5\0\20\14\3\0"+
    "\3\14\11\0\11\14\4\0\1\14\5\0\6\14\1\132"+
    "\11\14\3\0\3\14\11\0\5\14\1\133\3\14\4\0"+
    "\1\14\5\0\6\14\1\134\11\14\3\0\3\14\11\0"+
    "\11\14\4\0\1\14\5\0\3\14\1\135\14\14\3\0"+
    "\3\14\10\0\53\71\1\0\6\71\1\0\11\14\4\0"+
    "\1\14\5\0\1\136\17\14\3\0\3\14\11\0\10\14"+
    "\1\137\4\0\1\14\5\0\20\14\3\0\3\14\11\0"+
    "\6\14\1\140\2\14\4\0\1\14\5\0\20\14\3\0"+
    "\3\14\11\0\7\14\1\141\1\14\4\0\1\14\5\0"+
    "\20\14\3\0\3\14\11\0\5\14\1\142\3\14\4\0"+
    "\1\14\5\0\20\14\3\0\3\14\11\0\1\143\10\14"+
    "\4\0\1\14\5\0\20\14\3\0\3\14\11\0\11\14"+
    "\4\0\1\14\5\0\3\14\1\144\14\14\3\0\3\14"+
    "\11\0\7\14\1\145\1\14\4\0\1\14\5\0\20\14"+
    "\3\0\3\14\11\0\11\14\4\0\1\146\5\0\7\14"+
    "\1\147\3\14\1\147\4\14\3\0\1\147\2\14\11\0"+
    "\11\14\4\0\1\14\5\0\17\14\1\150\3\0\3\14"+
    "\11\0\5\14\1\151\3\14\4\0\1\14\5\0\20\14"+
    "\3\0\3\14\11\0\11\14\4\0\1\14\5\0\11\14"+
    "\1\152\6\14\3\0\3\14\11\0\4\14\1\103\4\14"+
    "\4\0\1\14\5\0\7\14\2\112\2\14\1\112\4\14"+
    "\3\0\1\112\2\14\10\0\52\113\1\153\7\113\1\0"+
    "\11\14\4\0\1\14\5\0\5\14\1\154\12\14\3\0"+
    "\3\14\11\0\7\14\1\155\1\14\4\0\1\14\5\0"+
    "\20\14\3\0\3\14\11\0\5\14\1\156\3\14\4\0"+
    "\1\14\5\0\20\14\3\0\3\14\11\0\3\14\1\157"+
    "\5\14\4\0\1\14\5\0\20\14\3\0\3\14\11\0"+
    "\4\14\1\160\4\14\4\0\1\14\5\0\20\14\3\0"+
    "\3\14\11\0\11\14\4\0\1\14\5\0\5\14\1\161"+
    "\12\14\3\0\3\14\11\0\4\14\1\162\4\14\4\0"+
    "\1\14\5\0\20\14\3\0\3\14\11\0\11\14\4\0"+
    "\1\14\5\0\7\14\1\163\10\14\3\0\3\14\11\0"+
    "\3\14\1\164\5\14\4\0\1\14\5\0\20\14\3\0"+
    "\3\14\11\0\5\14\1\165\3\14\4\0\1\14\5\0"+
    "\20\14\3\0\3\14\11\0\2\14\1\166\6\14\4\0"+
    "\1\14\5\0\20\14\3\0\3\14\11\0\5\14\1\167"+
    "\3\14\4\0\1\14\5\0\20\14\3\0\3\14\11\0"+
    "\2\14\1\170\6\14\4\0\1\14\5\0\20\14\3\0"+
    "\3\14\11\0\6\14\1\171\2\14\4\0\1\14\5\0"+
    "\20\14\3\0\3\14\11\0\2\14\1\172\6\14\4\0"+
    "\1\14\5\0\20\14\3\0\3\14\11\0\3\14\1\173"+
    "\5\14\4\0\1\14\5\0\20\14\3\0\3\14\11\0"+
    "\11\14\4\0\1\14\5\0\7\14\1\147\3\14\1\147"+
    "\4\14\3\0\1\147\2\14\11\0\11\14\4\0\1\14"+
    "\5\0\7\14\2\147\2\14\1\147\4\14\3\0\1\147"+
    "\2\14\11\0\4\14\1\174\4\14\4\0\1\14\5\0"+
    "\20\14\3\0\3\14\11\0\11\14\4\0\1\14\5\0"+
    "\3\14\1\175\14\14\3\0\3\14\11\0\4\14\1\176"+
    "\4\14\4\0\1\14\5\0\20\14\3\0\3\14\11\0"+
    "\2\14\1\177\6\14\4\0\1\14\5\0\20\14\3\0"+
    "\3\14\11\0\11\14\4\0\1\14\5\0\10\14\1\200"+
    "\7\14\3\0\3\14\11\0\11\14\4\0\1\14\5\0"+
    "\15\14\1\201\2\14\3\0\3\14\11\0\11\14\4\0"+
    "\1\14\5\0\13\14\1\202\4\14\3\0\3\14\11\0"+
    "\11\14\4\0\1\14\5\0\3\14\1\203\14\14\3\0"+
    "\3\14\11\0\11\14\4\0\1\14\5\0\16\14\1\204"+
    "\1\14\3\0\3\14\10\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[4200];
    int offset = 0;
    offset = zzUnpackTrans(ZZ_TRANS_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackTrans(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      value--;
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }


  /* error codes */
  private static final int ZZ_UNKNOWN_ERROR = 0;
  private static final int ZZ_NO_MATCH = 1;
  private static final int ZZ_PUSHBACK_2BIG = 2;

  /* error messages for the codes above */
  private static final String ZZ_ERROR_MSG[] = {
    "Unkown internal scanner error",
    "Error: could not match input",
    "Error: pushback value was too large"
  };

  /**
   * ZZ_ATTRIBUTE[aState] contains the attributes of state <code>aState</code>
   */
  private static final int [] ZZ_ATTRIBUTE = zzUnpackAttribute();

  private static final String ZZ_ATTRIBUTE_PACKED_0 =
    "\2\0\1\11\12\1\3\11\1\1\2\11\1\1\2\11"+
    "\14\1\4\11\1\1\1\11\17\1\1\11\16\1\3\11"+
    "\1\1\1\0\4\11\33\1\1\11\31\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[132];
    int offset = 0;
    offset = zzUnpackAttribute(ZZ_ATTRIBUTE_PACKED_0, offset, result);
    return result;
  }

  private static int zzUnpackAttribute(String packed, int offset, int [] result) {
    int i = 0;       /* index in packed string  */
    int j = offset;  /* index in unpacked array */
    int l = packed.length();
    while (i < l) {
      int count = packed.charAt(i++);
      int value = packed.charAt(i++);
      do result[j++] = value; while (--count > 0);
    }
    return j;
  }

  /** the input device */
  private java.io.Reader zzReader;

  /** the current state of the DFA */
  private int zzState;

  /** the current lexical state */
  private int zzLexicalState = YYINITIAL;

  /** this buffer contains the current text to be matched and is
      the source of the yytext() string */
  private char zzBuffer[] = new char[ZZ_BUFFERSIZE];

  /** the textposition at the last accepting state */
  private int zzMarkedPos;

  /** the current text position in the buffer */
  private int zzCurrentPos;

  /** startRead marks the beginning of the yytext() string in the buffer */
  private int zzStartRead;

  /** endRead marks the last character in the buffer, that has been read
      from input */
  private int zzEndRead;

  /** number of newlines encountered up to the start of the matched text */
  private int yyline;

  /** the number of characters up to the start of the matched text */
  private int yychar;

  /**
   * the number of characters from the last newline up to the start of the 
   * matched text
   */
  private int yycolumn;

  /** 
   * zzAtBOL == true <=> the scanner is currently at the beginning of a line
   */
  private boolean zzAtBOL = true;

  /** zzAtEOF == true <=> the scanner is at the EOF */
  private boolean zzAtEOF;

  /** denotes if the user-EOF-code has already been executed */
  private boolean zzEOFDone;

  /* user code: */
  // Author: FracPete (fracpete at waikato dot ac dot nz)
  // Version: $Revision$
  protected SymbolFactory sf;

  public Scanner(InputStream r, SymbolFactory sf){
    this(r);
    this.sf = sf;
  }
  StringBuilder string = new StringBuilder();


  /**
   * Creates a new scanner
   * There is also a java.io.InputStream version of this constructor.
   *
   * @param   in  the java.io.Reader to read input from.
   */
  public Scanner(java.io.Reader in) {
    this.zzReader = in;
  }

  /**
   * Creates a new scanner.
   * There is also java.io.Reader version of this constructor.
   *
   * @param   in  the java.io.Inputstream to read input from.
   */
  public Scanner(java.io.InputStream in) {
    this(new java.io.InputStreamReader(in));
  }

  /** 
   * Unpacks the compressed character translation table.
   *
   * @param packed   the packed character translation table
   * @return         the unpacked character translation table
   */
  private static char [] zzUnpackCMap(String packed) {
    char [] map = new char[0x10000];
    int i = 0;  /* index in packed string  */
    int j = 0;  /* index in unpacked array */
    while (i < 178) {
      int  count = packed.charAt(i++);
      char value = packed.charAt(i++);
      do map[j++] = value; while (--count > 0);
    }
    return map;
  }


  /**
   * Refills the input buffer.
   *
   * @return      <code>false</code>, iff there was new input.
   * 
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  private boolean zzRefill() throws java.io.IOException {

    /* first: make room (if you can) */
    if (zzStartRead > 0) {
      System.arraycopy(zzBuffer, zzStartRead,
                       zzBuffer, 0,
                       zzEndRead-zzStartRead);

      /* translate stored positions */
      zzEndRead-= zzStartRead;
      zzCurrentPos-= zzStartRead;
      zzMarkedPos-= zzStartRead;
      zzStartRead = 0;
    }

    /* is the buffer big enough? */
    if (zzCurrentPos >= zzBuffer.length) {
      /* if not: blow it up */
      char newBuffer[] = new char[zzCurrentPos*2];
      System.arraycopy(zzBuffer, 0, newBuffer, 0, zzBuffer.length);
      zzBuffer = newBuffer;
    }

    /* finally: fill the buffer with new input */
    int numRead = zzReader.read(zzBuffer, zzEndRead,
                                            zzBuffer.length-zzEndRead);

    if (numRead > 0) {
      zzEndRead+= numRead;
      return false;
    }
    // unlikely but not impossible: read 0 characters, but not at end of stream    
    if (numRead == 0) {
      int c = zzReader.read();
      if (c == -1) {
        return true;
      } else {
        zzBuffer[zzEndRead++] = (char) c;
        return false;
      }     
    }

	// numRead < 0
    return true;
  }

    
  /**
   * Closes the input stream.
   */
  public final void yyclose() throws java.io.IOException {
    zzAtEOF = true;            /* indicate end of file */
    zzEndRead = zzStartRead;  /* invalidate buffer    */

    if (zzReader != null)
      zzReader.close();
  }


  /**
   * Resets the scanner to read from a new input stream.
   * Does not close the old reader.
   *
   * All internal variables are reset, the old input stream 
   * <b>cannot</b> be reused (internal buffer is discarded and lost).
   * Lexical state is set to <tt>ZZ_INITIAL</tt>.
   *
   * @param reader   the new input stream 
   */
  public final void yyreset(java.io.Reader reader) {
    zzReader = reader;
    zzAtBOL  = true;
    zzAtEOF  = false;
    zzEOFDone = false;
    zzEndRead = zzStartRead = 0;
    zzCurrentPos = zzMarkedPos = 0;
    yyline = yychar = yycolumn = 0;
    zzLexicalState = YYINITIAL;
  }


  /**
   * Returns the current lexical state.
   */
  public final int yystate() {
    return zzLexicalState;
  }


  /**
   * Enters a new lexical state
   *
   * @param newState the new lexical state
   */
  public final void yybegin(int newState) {
    zzLexicalState = newState;
  }


  /**
   * Returns the text matched by the current regular expression.
   */
  public final String yytext() {
    return new String( zzBuffer, zzStartRead, zzMarkedPos-zzStartRead );
  }


  /**
   * Returns the character at position <tt>pos</tt> from the 
   * matched text. 
   * 
   * It is equivalent to yytext().charAt(pos), but faster
   *
   * @param pos the position of the character to fetch. 
   *            A value from 0 to yylength()-1.
   *
   * @return the character at position pos
   */
  public final char yycharat(int pos) {
    return zzBuffer[zzStartRead+pos];
  }


  /**
   * Returns the length of the matched text region.
   */
  public final int yylength() {
    return zzMarkedPos-zzStartRead;
  }


  /**
   * Reports an error that occured while scanning.
   *
   * In a wellformed scanner (no or only correct usage of 
   * yypushback(int) and a match-all fallback rule) this method 
   * will only be called with things that "Can't Possibly Happen".
   * If this method is called, something is seriously wrong
   * (e.g. a JFlex bug producing a faulty scanner etc.).
   *
   * Usual syntax/scanner level error handling should be done
   * in error fallback rules.
   *
   * @param   errorCode  the code of the errormessage to display
   */
  private void zzScanError(int errorCode) {
    String message;
    try {
      message = ZZ_ERROR_MSG[errorCode];
    }
    catch (ArrayIndexOutOfBoundsException e) {
      message = ZZ_ERROR_MSG[ZZ_UNKNOWN_ERROR];
    }

    throw new Error(message);
  } 


  /**
   * Pushes the specified amount of characters back into the input stream.
   *
   * They will be read again by then next call of the scanning method
   *
   * @param number  the number of characters to be read again.
   *                This number must not be greater than yylength()!
   */
  public void yypushback(int number)  {
    if ( number > yylength() )
      zzScanError(ZZ_PUSHBACK_2BIG);

    zzMarkedPos -= number;
  }


  /**
   * Resumes scanning until the next regular expression is matched,
   * the end of input is encountered or an I/O-Error occurs.
   *
   * @return      the next token
   * @exception   java.io.IOException  if any I/O-Error occurs
   */
  public java_cup.runtime.Symbol next_token() throws java.io.IOException {
    int zzInput;
    int zzAction;

    // cached fields:
    int zzCurrentPosL;
    int zzMarkedPosL;
    int zzEndReadL = zzEndRead;
    char [] zzBufferL = zzBuffer;
    char [] zzCMapL = ZZ_CMAP;

    int [] zzTransL = ZZ_TRANS;
    int [] zzRowMapL = ZZ_ROWMAP;
    int [] zzAttrL = ZZ_ATTRIBUTE;

    while (true) {
      zzMarkedPosL = zzMarkedPos;

      zzAction = -1;

      zzCurrentPosL = zzCurrentPos = zzStartRead = zzMarkedPosL;
  
      zzState = ZZ_LEXSTATE[zzLexicalState];


      zzForAction: {
        while (true) {
    
          if (zzCurrentPosL < zzEndReadL)
            zzInput = zzBufferL[zzCurrentPosL++];
          else if (zzAtEOF) {
            zzInput = YYEOF;
            break zzForAction;
          }
          else {
            // store back cached positions
            zzCurrentPos  = zzCurrentPosL;
            zzMarkedPos   = zzMarkedPosL;
            boolean eof = zzRefill();
            // get translated positions and possibly new buffer
            zzCurrentPosL  = zzCurrentPos;
            zzMarkedPosL   = zzMarkedPos;
            zzBufferL      = zzBuffer;
            zzEndReadL     = zzEndRead;
            if (eof) {
              zzInput = YYEOF;
              break zzForAction;
            }
            else {
              zzInput = zzBufferL[zzCurrentPosL++];
            }
          }
          int zzNext = zzTransL[ zzRowMapL[zzState] + zzCMapL[zzInput] ];
          if (zzNext == -1) break zzForAction;
          zzState = zzNext;

          int zzAttributes = zzAttrL[zzState];
          if ( (zzAttributes & 1) == 1 ) {
            zzAction = zzState;
            zzMarkedPosL = zzCurrentPosL;
            if ( (zzAttributes & 8) == 8 ) break zzForAction;
          }

        }
      }

      // store back cached position
      zzMarkedPos = zzMarkedPosL;

      switch (zzAction < 0 ? zzAction : ZZ_ACTION[zzAction]) {
        case 62: 
          { return sf.newSymbol("Atan2", sym.ATAN2);
          }
        case 64: break;
        case 27: 
          { return sf.newSymbol("Not qquals", sym.NOT_EQ);
          }
        case 65: break;
        case 41: 
          { return sf.newSymbol("Abs", sym.ABS);
          }
        case 66: break;
        case 48: 
          { return sf.newSymbol("TanH", sym.TANH);
          }
        case 67: break;
        case 53: 
          { return sf.newSymbol("Atan", sym.ATAN);
          }
        case 68: break;
        case 29: 
          { string.append('\t');
          }
        case 69: break;
        case 23: 
          { return sf.newSymbol("Assignment", sym.ASSIGNMENT);
          }
        case 70: break;
        case 44: 
          { return sf.newSymbol("Min", sym.MIN);
          }
        case 71: break;
        case 61: 
          { return sf.newSymbol("Log10", sym.LOG10);
          }
        case 72: break;
        case 18: 
          { string.setLength(0); yybegin(STRING);
          }
        case 73: break;
        case 38: 
          { return sf.newSymbol("Sin", sym.SIN);
          }
        case 74: break;
        case 15: 
          { /* ignore white space. */
          }
        case 75: break;
        case 5: 
          { return sf.newSymbol("Comma", sym.COMMA);
          }
        case 76: break;
        case 2: 
          { return sf.newSymbol("Variable", sym.VARIABLE, new String(yytext()));
          }
        case 77: break;
        case 20: 
          { yybegin(YYINITIAL);
                  return sf.newSymbol("String", sym.STRING, string.toString());
          }
        case 78: break;
        case 8: 
          { return sf.newSymbol("Times", sym.TIMES);
          }
        case 79: break;
        case 59: 
          { return sf.newSymbol("False", sym.FALSE);
          }
        case 80: break;
        case 58: 
          { return sf.newSymbol("Floor", sym.FLOOR);
          }
        case 81: break;
        case 51: 
          { return sf.newSymbol("SinH", sym.SINH);
          }
        case 82: break;
        case 21: 
          { string.append('\\');
          }
        case 83: break;
        case 12: 
          { return sf.newSymbol("Number", sym.NUMBER, new Double(yytext()));
          }
        case 84: break;
        case 43: 
          { return sf.newSymbol("Pow", sym.POW);
          }
        case 85: break;
        case 13: 
          { return sf.newSymbol("Less than", sym.LT);
          }
        case 86: break;
        case 31: 
          { string.append('\r');
          }
        case 87: break;
        case 55: 
          { return sf.newSymbol("Ceil", sym.CEIL);
          }
        case 88: break;
        case 60: 
          { return sf.newSymbol("Hypot", sym.HYPOT);
          }
        case 89: break;
        case 40: 
          { return sf.newSymbol("All", sym.ALL);
          }
        case 90: break;
        case 17: 
          { return sf.newSymbol("Right Bracket", sym.RPAREN);
          }
        case 91: break;
        case 63: 
          { return sf.newSymbol("Signum", sym.SIGNUM);
          }
        case 92: break;
        case 56: 
          { return sf.newSymbol("Cbrt", sym.CBRT);
          }
        case 93: break;
        case 49: 
          { return sf.newSymbol("True", sym.TRUE);
          }
        case 94: break;
        case 33: 
          { return sf.newSymbol("Tan", sym.TAN);
          }
        case 95: break;
        case 4: 
          { return sf.newSymbol("Semi", sym.SEMI);
          }
        case 96: break;
        case 46: 
          { return sf.newSymbol("Variable", sym.VARIABLE, new String(yytext().replace("'", "").replace("'", "")));
          }
        case 97: break;
        case 32: 
          { string.append('\"');
          }
        case 98: break;
        case 3: 
          { return sf.newSymbol("Equals", sym.EQ);
          }
        case 99: break;
        case 10: 
          { return sf.newSymbol("Power", sym.EXPONENT);
          }
        case 100: break;
        case 28: 
          { return sf.newSymbol("Greater or equal than", sym.GE);
          }
        case 101: break;
        case 22: 
          { return sf.newSymbol("If",   sym.IF);
          }
        case 102: break;
        case 42: 
          { return sf.newSymbol("Cos", sym.COS);
          }
        case 103: break;
        case 52: 
          { return sf.newSymbol("Sqrt", sym.SQRT);
          }
        case 104: break;
        case 37: 
          { return sf.newSymbol("Log", sym.LOG);
          }
        case 105: break;
        case 24: 
          { /* ignore line comments. */
          }
        case 106: break;
        case 57: 
          { return sf.newSymbol("CosH", sym.COSH);
          }
        case 107: break;
        case 6: 
          { return sf.newSymbol("Minus", sym.MINUS);
          }
        case 108: break;
        case 7: 
          { return sf.newSymbol("Plus", sym.PLUS);
          }
        case 109: break;
        case 30: 
          { string.append('\n');
          }
        case 110: break;
        case 54: 
          { return sf.newSymbol("Rint", sym.RINT);
          }
        case 111: break;
        case 50: 
          { return sf.newSymbol("Else", sym.ELSE);
          }
        case 112: break;
        case 11: 
          { return sf.newSymbol("Modulo", sym.MODULO);
          }
        case 113: break;
        case 47: 
          { return sf.newSymbol("Then", sym.THEN);
          }
        case 114: break;
        case 26: 
          { return sf.newSymbol("Less or equal than", sym.LE);
          }
        case 115: break;
        case 16: 
          { return sf.newSymbol("Left Bracket", sym.LPAREN);
          }
        case 116: break;
        case 19: 
          { string.append(yytext());
          }
        case 117: break;
        case 35: 
          { return sf.newSymbol("Exp", sym.EXP);
          }
        case 118: break;
        case 9: 
          { return sf.newSymbol("Division", sym.DIVISION);
          }
        case 119: break;
        case 34: 
          { return sf.newSymbol("End",  sym.END);
          }
        case 120: break;
        case 1: 
          { System.err.println("Illegal character: "+yytext());
          }
        case 121: break;
        case 36: 
          { return sf.newSymbol("Not", sym.NOT);
          }
        case 122: break;
        case 25: 
          { return sf.newSymbol("Or", sym.OR);
          }
        case 123: break;
        case 14: 
          { return sf.newSymbol("Greater than", sym.GT);
          }
        case 124: break;
        case 45: 
          { return sf.newSymbol("Max", sym.MAX);
          }
        case 125: break;
        case 39: 
          { return sf.newSymbol("And", sym.AND);
          }
        case 126: break;
        default: 
          if (zzInput == YYEOF && zzStartRead == zzCurrentPos) {
            zzAtEOF = true;
              {     return sf.newSymbol("EOF",sym.EOF);
 }
          } 
          else {
            zzScanError(ZZ_NO_MATCH);
          }
      }
    }
  }


}
