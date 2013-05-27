/**
 *
 * Copyright (C) 2013 - Luís A. Bastião Silva and Universidade de Aveiro This
 * program is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 */
package pt.ua.ieeta.jdicomanonymizer;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import org.dcm4che2.data.DicomObject;
import org.dcm4che2.data.Tag;
import org.dcm4che2.data.VR;
import org.dcm4che2.io.DicomInputStream;
import org.dcm4che2.io.DicomOutputStream;

/**
 *
 * @author: Luís A. Bastião Silva <bastiao@ua.pt>
 */
public class Anonymize {

    public void anonymize(File src, File dest) throws IOException {

        DicomInputStream dis = new DicomInputStream(src);
        DicomObject obj = null;
        try {
            obj = dis.readDicomObject();
            obj.putString(Tag.PatientName, VR.PN, "Patient 1");
            obj.putString(Tag.AccessionNumber, VR.SH, "123456789");
            obj.putString(Tag.InstitutionName, VR.LO, "Hospital");
            obj.putString(Tag.PatientID, VR.LO, "123456");
            obj.putString(Tag.PatientBirthDate, VR.DA, "19880129");
            
        } finally {
            dis.close();
        }
        
        File f = dest;
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }
        BufferedOutputStream bos = new BufferedOutputStream(fos);
        DicomOutputStream dos = new DicomOutputStream(bos);
        try {
            dos.writeDicomFile(obj);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        } finally {
            try {
                dos.close();
            } catch (IOException ignore) {
            }
        }



    }
}
