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

package org.katkov.lj.xmlrpc.results;

import java.util.Arrays;
import java.util.Map;

public class ConsoleCommandResult {
    private Boolean success;
    private OutputLine[] output;


    public ConsoleCommandResult(Map map) {
        success = map.get("success") != null && (Integer) map.get("success") > 0;
        output = createOutputLines((Object[]) map.get("output"));
    }

    private static OutputLine[] createOutputLines(Object[] objects) {
        if (objects == null) {
            return null;
        }
        OutputLine[] result = new OutputLine[objects.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = new OutputLine((String) ((Object[]) objects[i])[0], (String) ((Object[]) objects[i])[1]);
        }
        return result;
    }


    public Boolean getSuccess() {
        return success;
    }

    public OutputLine[] getOutput() {
        return output;
    }


    public String toString() {
        return "ConsoleCommandResult{" +
                "success=" + success +
                ", output=" + (output == null ? null : Arrays.asList(output)) +
                '}';
    }

    static class OutputLine {
        private String type;
        private String out;


        public OutputLine(String type, String out) {
            this.out = out;
            this.type = type;
        }


        public String getType() {
            return type;
        }

        public String getOut() {
            return out;
        }

        public String toString() {
            return "OutputLine{" +
                    "type='" + type + '\'' +
                    ", arguments='" + out + '\'' +
                    '}';
        }
    }
}
