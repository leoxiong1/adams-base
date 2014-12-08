/* The following code was generated by JFlex 1.4.2 on 9/12/14 11:53 AM */

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
 * Copyright (C) 2013-2014 University of Waikato, Hamilton, New Zealand
 */

package adams.parser.spreadsheetquery;

import java_cup.runtime.SymbolFactory;
import java.io.*;

/**
 * A scanner for spreadsheet queries.
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
    "\11\0\1\45\1\44\1\0\1\45\1\53\22\0\1\45\1\34\1\51"+
    "\2\0\1\30\2\0\1\47\1\50\1\27\1\0\1\46\1\37\1\36"+
    "\1\0\1\35\11\40\2\0\1\31\1\32\1\33\2\0\1\6\1\16"+
    "\1\4\1\11\1\2\1\41\1\22\1\13\1\20\2\41\1\3\1\24"+
    "\1\21\1\15\1\10\1\26\1\14\1\1\1\5\1\7\1\25\1\12"+
    "\1\23\1\17\1\41\1\42\1\52\1\43\1\0\1\41\1\0\1\6"+
    "\1\16\1\4\1\11\1\2\1\41\1\22\1\13\1\20\2\41\1\3"+
    "\1\24\1\21\1\15\1\10\1\26\1\14\1\1\1\5\1\7\1\25"+
    "\1\12\1\23\1\17\1\41\uff85\0";

  /** 
   * Translates characters to character classes
   */
  private static final char [] ZZ_CMAP = zzUnpackCMap(ZZ_CMAP_PACKED);

  /** 
   * Translates DFA states to action switch labels.
   */
  private static final int [] ZZ_ACTION = zzUnpackAction();

  private static final String ZZ_ACTION_PACKED_0 =
    "\2\0\1\1\21\2\1\3\1\4\1\5\1\6\1\7"+
    "\1\1\1\10\1\1\1\2\1\1\1\11\1\12\1\13"+
    "\1\14\1\15\1\16\1\17\1\20\6\2\1\21\10\2"+
    "\1\22\1\23\1\24\10\2\1\25\1\26\1\27\1\2"+
    "\1\0\1\10\1\2\1\0\1\30\1\31\1\32\1\33"+
    "\1\2\1\34\1\2\1\35\3\2\1\36\1\37\12\2"+
    "\1\40\1\2\1\41\3\2\1\42\1\43\1\2\1\10"+
    "\1\0\1\44\1\45\10\2\1\46\6\2\1\47\1\2"+
    "\1\50\1\2\1\0\1\10\1\2\1\51\1\52\1\2"+
    "\1\53\2\2\1\54\1\2\1\55\2\2\1\56\1\2"+
    "\1\57\1\2\1\60\1\61\2\2\1\62\1\63\1\64"+
    "\1\65\1\2\1\66\1\2\1\67\1\2\1\70\5\2"+
    "\1\71";

  private static int [] zzUnpackAction() {
    int [] result = new int[165];
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
    "\0\0\0\54\0\130\0\204\0\260\0\334\0\u0108\0\u0134"+
    "\0\u0160\0\u018c\0\u01b8\0\u01e4\0\u0210\0\u023c\0\u0268\0\u0294"+
    "\0\u02c0\0\u02ec\0\u0318\0\u0344\0\130\0\130\0\u0370\0\130"+
    "\0\u039c\0\u03c8\0\u03f4\0\u0420\0\u044c\0\u0478\0\130\0\130"+
    "\0\130\0\130\0\130\0\u04a4\0\130\0\u04d0\0\u04fc\0\u0528"+
    "\0\u0554\0\u0580\0\u05ac\0\u05d8\0\u0604\0\u0630\0\u065c\0\u0688"+
    "\0\u06b4\0\u06e0\0\u070c\0\u0738\0\u0764\0\u0790\0\260\0\260"+
    "\0\u07bc\0\u07e8\0\u0814\0\u0840\0\u086c\0\u0898\0\u08c4\0\u08f0"+
    "\0\130\0\130\0\130\0\u091c\0\u0420\0\u0948\0\u0974\0\u09a0"+
    "\0\130\0\130\0\130\0\130\0\u09cc\0\260\0\u09f8\0\260"+
    "\0\u0a24\0\u0a50\0\u0a7c\0\260\0\260\0\u0aa8\0\u0ad4\0\u0b00"+
    "\0\u0b2c\0\u0b58\0\u0b84\0\u0bb0\0\u0bdc\0\u0c08\0\u0c34\0\260"+
    "\0\u0c60\0\260\0\u0c8c\0\u0cb8\0\u0ce4\0\260\0\260\0\u0d10"+
    "\0\u0d3c\0\u0d68\0\u0d94\0\130\0\u0dc0\0\u0dec\0\u0e18\0\u0e44"+
    "\0\u0e70\0\u0e9c\0\u0ec8\0\u0ef4\0\260\0\u0f20\0\u0f4c\0\u0f78"+
    "\0\u0fa4\0\u0fd0\0\u0ffc\0\260\0\u1028\0\260\0\u1054\0\u1080"+
    "\0\u10ac\0\u10d8\0\u1104\0\260\0\u1130\0\260\0\u115c\0\u1188"+
    "\0\260\0\u11b4\0\260\0\u11e0\0\u120c\0\260\0\u1238\0\260"+
    "\0\u1264\0\260\0\260\0\u1290\0\u12bc\0\260\0\260\0\260"+
    "\0\260\0\u12e8\0\260\0\u1314\0\260\0\u1340\0\260\0\u136c"+
    "\0\u1398\0\u13c4\0\u13f0\0\u141c\0\260";

  private static int [] zzUnpackRowMap() {
    int [] result = new int[165];
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
    "\1\3\1\4\1\5\1\6\1\7\1\5\1\10\1\11"+
    "\1\12\1\13\1\14\1\15\1\16\1\17\1\20\1\5"+
    "\1\21\1\22\1\23\1\5\1\24\2\5\1\25\1\26"+
    "\1\27\1\30\1\31\1\32\1\33\1\34\1\35\1\33"+
    "\1\5\1\36\1\3\2\37\1\40\1\41\1\42\1\43"+
    "\1\3\1\37\44\44\1\0\4\44\1\45\1\46\1\3"+
    "\55\0\1\5\1\47\2\5\1\50\1\5\1\51\17\5"+
    "\6\0\1\5\1\0\3\5\13\0\26\5\6\0\1\5"+
    "\1\0\3\5\13\0\17\5\1\52\6\5\6\0\1\5"+
    "\1\0\3\5\13\0\1\5\1\53\12\5\1\54\11\5"+
    "\6\0\1\5\1\0\3\5\13\0\1\55\17\5\1\56"+
    "\3\5\1\57\1\5\6\0\1\5\1\0\3\5\13\0"+
    "\7\5\1\60\16\5\6\0\1\5\1\0\3\5\13\0"+
    "\5\5\1\61\20\5\6\0\1\5\1\0\3\5\13\0"+
    "\1\5\1\62\24\5\6\0\1\5\1\0\3\5\13\0"+
    "\12\5\1\63\13\5\6\0\1\5\1\0\3\5\13\0"+
    "\5\5\1\64\20\5\6\0\1\5\1\0\3\5\13\0"+
    "\1\5\1\65\24\5\6\0\1\5\1\0\3\5\13\0"+
    "\13\5\1\66\12\5\6\0\1\5\1\0\3\5\13\0"+
    "\16\5\1\67\7\5\6\0\1\5\1\0\3\5\13\0"+
    "\1\70\17\5\1\71\4\5\1\72\6\0\1\5\1\0"+
    "\3\5\13\0\6\5\1\73\5\5\1\74\11\5\6\0"+
    "\1\5\1\0\3\5\13\0\13\5\1\75\12\5\6\0"+
    "\1\5\1\0\3\5\13\0\1\5\1\76\3\5\1\77"+
    "\11\5\1\100\6\5\6\0\1\5\1\0\3\5\44\0"+
    "\1\101\1\102\52\0\1\103\53\0\1\102\22\0\1\5"+
    "\1\104\24\5\6\0\1\33\1\105\1\5\1\33\1\5"+
    "\47\0\1\106\2\0\1\106\14\0\26\5\6\0\1\5"+
    "\1\0\1\107\2\5\12\0\43\110\1\0\10\110\44\44"+
    "\1\0\4\44\10\0\1\111\6\0\1\112\4\0\1\113"+
    "\27\0\1\114\3\0\2\5\1\115\1\5\1\116\21\5"+
    "\6\0\1\5\1\0\3\5\13\0\10\5\1\117\15\5"+
    "\6\0\1\5\1\0\3\5\13\0\23\5\1\120\2\5"+
    "\6\0\1\5\1\0\3\5\13\0\23\5\1\121\2\5"+
    "\6\0\1\5\1\0\3\5\13\0\2\5\1\122\23\5"+
    "\6\0\1\5\1\0\3\5\13\0\6\5\1\123\17\5"+
    "\6\0\1\5\1\0\3\5\13\0\3\5\1\124\22\5"+
    "\6\0\1\5\1\0\3\5\13\0\10\5\1\125\15\5"+
    "\6\0\1\5\1\0\3\5\13\0\1\5\1\126\24\5"+
    "\6\0\1\5\1\0\3\5\13\0\10\5\1\127\15\5"+
    "\6\0\1\5\1\0\3\5\13\0\13\5\1\130\12\5"+
    "\6\0\1\5\1\0\3\5\13\0\1\131\1\5\1\132"+
    "\23\5\6\0\1\5\1\0\3\5\13\0\1\5\1\133"+
    "\24\5\6\0\1\5\1\0\3\5\13\0\24\5\1\134"+
    "\1\5\6\0\1\5\1\0\3\5\13\0\21\5\1\135"+
    "\4\5\6\0\1\5\1\0\3\5\13\0\10\5\1\136"+
    "\15\5\6\0\1\5\1\0\3\5\13\0\4\5\1\137"+
    "\21\5\6\0\1\5\1\0\3\5\13\0\13\5\1\140"+
    "\12\5\6\0\1\5\1\0\3\5\13\0\2\5\1\141"+
    "\23\5\6\0\1\5\1\0\3\5\13\0\4\5\1\142"+
    "\21\5\6\0\1\5\1\0\3\5\13\0\14\5\1\143"+
    "\11\5\6\0\1\5\1\0\3\5\13\0\5\5\1\144"+
    "\2\5\1\145\15\5\6\0\1\5\1\0\3\5\13\0"+
    "\22\5\1\146\3\5\6\0\1\5\1\0\3\5\13\0"+
    "\20\5\1\147\5\5\6\0\1\5\1\0\3\5\13\0"+
    "\26\5\6\0\1\5\1\0\1\150\1\151\1\5\14\0"+
    "\1\152\32\0\1\106\2\0\1\106\13\0\1\153\26\107"+
    "\6\153\1\107\1\153\3\107\2\153\1\0\7\153\43\110"+
    "\1\154\10\110\1\0\1\5\1\155\24\5\6\0\1\5"+
    "\1\0\3\5\13\0\1\5\1\156\24\5\6\0\1\5"+
    "\1\0\3\5\13\0\17\5\1\157\6\5\6\0\1\5"+
    "\1\0\3\5\13\0\2\5\1\160\23\5\6\0\1\5"+
    "\1\0\3\5\13\0\20\5\1\161\5\5\6\0\1\5"+
    "\1\0\3\5\13\0\13\5\1\162\12\5\6\0\1\5"+
    "\1\0\3\5\13\0\5\5\1\163\20\5\6\0\1\5"+
    "\1\0\3\5\13\0\1\164\25\5\6\0\1\5\1\0"+
    "\3\5\13\0\3\5\1\165\22\5\6\0\1\5\1\0"+
    "\3\5\13\0\1\5\1\166\24\5\6\0\1\5\1\0"+
    "\3\5\13\0\13\5\1\167\12\5\6\0\1\5\1\0"+
    "\3\5\13\0\17\5\1\170\6\5\6\0\1\5\1\0"+
    "\3\5\13\0\1\5\1\171\24\5\6\0\1\5\1\0"+
    "\3\5\13\0\1\5\1\172\24\5\6\0\1\5\1\0"+
    "\3\5\13\0\1\5\1\173\24\5\6\0\1\5\1\0"+
    "\3\5\13\0\2\5\1\174\23\5\6\0\1\5\1\0"+
    "\3\5\13\0\6\5\1\175\17\5\6\0\1\5\1\0"+
    "\3\5\13\0\20\5\1\176\5\5\6\0\1\5\1\0"+
    "\3\5\13\0\17\5\1\177\6\5\6\0\1\5\1\0"+
    "\3\5\13\0\26\5\6\0\1\5\1\0\1\5\1\151"+
    "\1\5\13\0\26\5\6\0\1\151\1\0\1\5\1\151"+
    "\1\5\51\0\1\200\1\201\13\0\44\153\1\0\7\153"+
    "\1\0\3\5\1\202\22\5\6\0\1\5\1\0\3\5"+
    "\13\0\24\5\1\203\1\5\6\0\1\5\1\0\3\5"+
    "\13\0\4\5\1\204\21\5\6\0\1\5\1\0\3\5"+
    "\13\0\4\5\1\205\21\5\6\0\1\5\1\0\3\5"+
    "\13\0\4\5\1\206\21\5\6\0\1\5\1\0\3\5"+
    "\13\0\5\5\1\207\20\5\6\0\1\5\1\0\3\5"+
    "\13\0\4\5\1\210\21\5\6\0\1\5\1\0\3\5"+
    "\13\0\1\5\1\211\24\5\6\0\1\5\1\0\3\5"+
    "\13\0\4\5\1\212\21\5\6\0\1\5\1\0\3\5"+
    "\13\0\1\5\1\213\24\5\6\0\1\5\1\0\3\5"+
    "\13\0\20\5\1\214\5\5\6\0\1\5\1\0\3\5"+
    "\13\0\22\5\1\215\3\5\6\0\1\5\1\0\3\5"+
    "\13\0\13\5\1\216\12\5\6\0\1\5\1\0\3\5"+
    "\13\0\13\5\1\217\12\5\6\0\1\5\1\0\3\5"+
    "\13\0\7\5\1\220\16\5\6\0\1\5\1\0\3\5"+
    "\13\0\5\5\1\221\20\5\6\0\1\5\1\0\3\5"+
    "\52\0\1\201\50\0\1\201\2\0\1\201\14\0\4\5"+
    "\1\222\21\5\6\0\1\5\1\0\3\5\13\0\7\5"+
    "\1\223\16\5\6\0\1\5\1\0\3\5\13\0\16\5"+
    "\1\224\7\5\6\0\1\5\1\0\3\5\13\0\21\5"+
    "\1\225\4\5\6\0\1\5\1\0\3\5\13\0\1\5"+
    "\1\226\24\5\6\0\1\5\1\0\3\5\13\0\1\5"+
    "\1\227\24\5\6\0\1\5\1\0\3\5\13\0\21\5"+
    "\1\230\4\5\6\0\1\5\1\0\3\5\13\0\7\5"+
    "\1\231\16\5\6\0\1\5\1\0\3\5\13\0\25\5"+
    "\1\232\6\0\1\5\1\0\3\5\13\0\20\5\1\233"+
    "\5\5\6\0\1\5\1\0\3\5\13\0\7\5\1\234"+
    "\16\5\6\0\1\5\1\0\3\5\13\0\1\5\1\235"+
    "\24\5\6\0\1\5\1\0\3\5\13\0\6\5\1\236"+
    "\17\5\6\0\1\5\1\0\3\5\13\0\1\5\1\237"+
    "\24\5\6\0\1\5\1\0\3\5\13\0\5\5\1\240"+
    "\20\5\6\0\1\5\1\0\3\5\13\0\13\5\1\241"+
    "\12\5\6\0\1\5\1\0\3\5\13\0\4\5\1\242"+
    "\21\5\6\0\1\5\1\0\3\5\13\0\17\5\1\243"+
    "\6\5\6\0\1\5\1\0\3\5\13\0\2\5\1\244"+
    "\23\5\6\0\1\5\1\0\3\5\13\0\1\5\1\245"+
    "\24\5\6\0\1\5\1\0\3\5\12\0";

  private static int [] zzUnpackTrans() {
    int [] result = new int[5192];
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
    "\2\0\1\11\21\1\2\11\1\1\1\11\6\1\5\11"+
    "\1\1\1\11\33\1\3\11\1\1\1\0\2\1\1\0"+
    "\4\11\35\1\1\0\1\1\1\11\23\1\1\0\45\1";

  private static int [] zzUnpackAttribute() {
    int [] result = new int[165];
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
    while (i < 170) {
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
        case 38: 
          { return sf.newSymbol("Desc",      sym.DESC);
          }
        case 58: break;
        case 19: 
          { return sf.newSymbol("By",        sym.BY);
          }
        case 59: break;
        case 22: 
          { return sf.newSymbol("Not qquals", sym.NOT_EQ);
          }
        case 60: break;
        case 49: 
          { return sf.newSymbol("StDevP",    sym.STDEVP);
          }
        case 61: break;
        case 30: 
          { return sf.newSymbol("Asc",       sym.ASC);
          }
        case 62: break;
        case 44: 
          { return sf.newSymbol("Parse",     sym.PARSE);
          }
        case 63: break;
        case 24: 
          { string.append('\t');
          }
        case 64: break;
        case 13: 
          { string.setLength(0); yybegin(STRING);
          }
        case 65: break;
        case 55: 
          { return sf.newSymbol("Average",   sym.AVERAGE);
          }
        case 66: break;
        case 9: 
          { /* ignore white space. */
          }
        case 67: break;
        case 10: 
          { return sf.newSymbol("Comma", sym.COMMA);
          }
        case 68: break;
        case 15: 
          { yybegin(YYINITIAL);
                  return sf.newSymbol("String", sym.STRING, string.toString());
          }
        case 69: break;
        case 41: 
          { return sf.newSymbol("StdDev",    sym.STDEV);
          }
        case 70: break;
        case 40: 
          { return sf.newSymbol("Mean",      sym.AVERAGE);
          }
        case 71: break;
        case 48: 
          { return sf.newSymbol("Select",    sym.SELECT);
          }
        case 72: break;
        case 42: 
          { return sf.newSymbol("Limit",     sym.LIMIT);
          }
        case 73: break;
        case 39: 
          { return sf.newSymbol("Null",      sym.NULL);
          }
        case 74: break;
        case 16: 
          { string.append('\\');
          }
        case 75: break;
        case 8: 
          { return sf.newSymbol("Number", sym.NUMBER, new Double(yytext()));
          }
        case 76: break;
        case 57: 
          { return sf.newSymbol("IQR",   sym.IQR);
          }
        case 77: break;
        case 29: 
          { return sf.newSymbol("Sum",       sym.SUM);
          }
        case 78: break;
        case 5: 
          { return sf.newSymbol("Less than", sym.LT);
          }
        case 79: break;
        case 25: 
          { string.append('\r');
          }
        case 80: break;
        case 20: 
          { return sf.newSymbol("Is",        sym.IS);
          }
        case 81: break;
        case 3: 
          { return sf.newSymbol("Star",    sym.STAR);
          }
        case 82: break;
        case 45: 
          { return sf.newSymbol("Where",     sym.WHERE);
          }
        case 83: break;
        case 12: 
          { return sf.newSymbol("Right Bracket", sym.RPAREN);
          }
        case 84: break;
        case 43: 
          { return sf.newSymbol("Count",     sym.COUNT);
          }
        case 85: break;
        case 54: 
          { return sf.newSymbol("Median",    sym.MEDIAN);
          }
        case 86: break;
        case 56: 
          { return sf.newSymbol("CellType",  sym.CELLTYPE);
          }
        case 87: break;
        case 50: 
          { return sf.newSymbol("Update",    sym.UPDATE);
          }
        case 88: break;
        case 2: 
          { return sf.newSymbol("Column", sym.COLUMN, new String(yytext()));
          }
        case 89: break;
        case 27: 
          { string.append('\"');
          }
        case 90: break;
        case 6: 
          { return sf.newSymbol("Equals", sym.EQ);
          }
        case 91: break;
        case 35: 
          { return sf.newSymbol("Min",       sym.MIN);
          }
        case 92: break;
        case 23: 
          { return sf.newSymbol("Greater or equal than", sym.GE);
          }
        case 93: break;
        case 36: 
          { /* ignore line comments. */
          }
        case 94: break;
        case 51: 
          { return sf.newSymbol("Delete",    sym.DELETE);
          }
        case 95: break;
        case 4: 
          { return sf.newSymbol("Percent", sym.PERCENT);
          }
        case 96: break;
        case 26: 
          { string.append('\n');
          }
        case 97: break;
        case 21: 
          { return sf.newSymbol("Less or equal than", sym.LE);
          }
        case 98: break;
        case 11: 
          { return sf.newSymbol("Left Bracket", sym.LPAREN);
          }
        case 99: break;
        case 14: 
          { string.append(yytext());
          }
        case 100: break;
        case 32: 
          { return sf.newSymbol("IQR",       sym.IQR);
          }
        case 101: break;
        case 47: 
          { return sf.newSymbol("Group",     sym.GROUP);
          }
        case 102: break;
        case 34: 
          { return sf.newSymbol("Max",       sym.MAX);
          }
        case 103: break;
        case 28: 
          { return sf.newSymbol("Set",       sym.SET);
          }
        case 104: break;
        case 1: 
          { System.err.println("Illegal character: "+yytext());
          }
        case 105: break;
        case 33: 
          { return sf.newSymbol("Not", sym.NOT);
          }
        case 106: break;
        case 53: 
          { return sf.newSymbol("RegExp",    sym.REGEXP);
          }
        case 107: break;
        case 18: 
          { return sf.newSymbol("Or", sym.OR);
          }
        case 108: break;
        case 7: 
          { return sf.newSymbol("Greater than", sym.GT);
          }
        case 109: break;
        case 46: 
          { return sf.newSymbol("Order",     sym.ORDER);
          }
        case 110: break;
        case 52: 
          { return sf.newSymbol("Having",    sym.HAVING);
          }
        case 111: break;
        case 37: 
          { return sf.newSymbol("Column", sym.COLUMN, new String(yytext().replace("[", "").replace("]", "")));
          }
        case 112: break;
        case 17: 
          { return sf.newSymbol("As",        sym.AS);
          }
        case 113: break;
        case 31: 
          { return sf.newSymbol("And", sym.AND);
          }
        case 114: break;
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
