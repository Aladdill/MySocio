/*
 * Copyright (c) 2006, Igor Katkov
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided 
 * that the following conditions are met:
 *
 *     * Redistributions of source code must retain the above copyright notice, this list of conditions 
 *       and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright notice, this list of conditions 
 *       and the following disclaimer in the documentation and/or other materials provided with the distribution.
 *     * The name of the author may not be used may not be used to endorse or 
 *       promote products derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" 
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, 
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR 
 * PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS 
 * BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, 
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT 
 * OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; 
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) 
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF 
 * THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.katkov.lj.xmlrpc.arguments;

public class LoginArgument extends BaseArgument {


    public LoginArgument() {
        setClientVersion("Java-LivejournalAPI/0.1");
    }

    public void setGetmoods(int getmoods) {
        struct.put("getmoods", getmoods);
    }

    /**
     * @param getmenus Send something for this key if you want to get a list/tree of web jump menus to show results your client.
     * @@optional
     */
    public void setGetmenus(boolean getmenus) {
        struct.put("getmenus", getmenus);
    }

    /**
     * @param getpickws If your client supports picture keywords and you want to receive that list, send something for this key, like "1", and you'll receieve the list of picture keywords the user has defined.
     * @@optional
     */
    public void setGetpickws(boolean getpickws) {
        struct.put("getpickws", getpickws);
    }

    /**
     * @param getpickwurls f your client supports picture keywords and can also display the pictures somehow, send something for this key, like "1", and you'll receieve the list of picture keyword URLs that correspond to the picture keywords as well as the URL for the default picture. You must send getpickws for this option to even matter.
     * @@optional
     */
    public void setGetpickwurls(boolean getpickwurls) {
        struct.put("getpickwurls", getpickwurls);
    }

    /**
     * @param clientversion Although optional, this should be a string of the form Platform-ProductName/ClientVersionMajor.Minor.Rev, like Win32-MFC/1.2.7 or Gtk-LoserJabber/1.0.4. Note results this case that "Gtk" is not a platform, but rather a toolkit, since the toolkit is multi-platform (Linux, FreeBSD, Solaris, Windows...). You make the judge what is best to send, but if it's of this form, we'll give you cool statistics about your users.
     * @@optional
     */
    public void setClientVersion(String clientversion) {
        struct.put("clientversion", clientversion);
    }


}
