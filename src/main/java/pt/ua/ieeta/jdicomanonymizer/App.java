/**
 * 
* Copyright (C) 2013 - Luís A. Bastião Silva and Universidade de Aveiro
* This program is free software: you can redistribute it and/or modify
* it under the terms of the GNU General Public License as published by
* the Free Software Foundation, either version 3 of the License, or
* (at your option) any later version.

* This program is distributed in the hope that it will be useful,
* but WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
* GNU General Public License for more details.

* You should have received a copy of the GNU General Public License
* along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package pt.ua.ieeta.jdicomanonymizer;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Main program
 * @author: Luís A. Bastião Silva <bastiao@ua.pt>
 */
public class App 
{
    public static void main( String[] args )
    {
        if (args.length!= 2)
        {
            System.err.println("You should use it: java -jar jDICOMAnonymizer-jar-with-dependencies.jar input.dcm output.dcm");
            return;
        }
        String input = args[0];
        String output = args[1];
        
        File in = new File(input);
        if (in.isDirectory())
        {
            anonymizeDirectories(new File(input), new File(output));
        }
        else
        {
            Anonymize an = new Anonymize();
            try 
            {
                an.anonymize(new File(input), new File(output));
            } catch (IOException ex) {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
    }
    
    
    public static void anonymizeDirectories(File input, File output)
    {
        Anonymize an = new Anonymize();
        File [] dicomFiles = input.listFiles();
        for (File f : dicomFiles)
        {
            try 
            {
                
                File out = new File(output, f.getName());
                System.out.println(f.getAbsolutePath());
                System.out.println(out.getAbsolutePath());
                an.anonymize(f, out);
            } 
            catch (IOException ex) 
            {
                Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    
}
