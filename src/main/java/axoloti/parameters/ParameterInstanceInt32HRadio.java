/**
 * Copyright (C) 2013, 2014, 2015 Johannes Taelman
 *
 * This file is part of Axoloti.
 *
 * Axoloti is free software: you can redistribute it and/or modify it under the
 * terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or (at your option) any later
 * version.
 *
 * Axoloti is distributed in the hope that it will be useful, but WITHOUT ANY
 * WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR
 * A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along with
 * Axoloti. If not, see <http://www.gnu.org/licenses/>.
 */
package axoloti.parameters;

import components.AssignMidiCCMenuItems;
import components.control.HRadioComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import org.simpleframework.xml.Attribute;

/**
 *
 * @author Johannes Taelman
 */
public class ParameterInstanceInt32HRadio extends ParameterInstanceInt32 {

    public ParameterInstanceInt32HRadio() {
    }

    public ParameterInstanceInt32HRadio(@Attribute(name = "value") int v) {
        super(v);
    }

    @Override
    public void updateV() {
        ctrl.setValue(value.getInt());
    }

    @Override
    public String GenerateCodeInit(String vprefix, String StructAccces) {
        String s = /*"    " + variableName(vprefix) + " = " + (value.getInt()) + ";\n"
                 + "    " + valueName(vprefix) + " = " + (value.getInt()) + ";\n"
                 + "    " + signalsName(vprefix) + " = 0;\n"
                 + "    SetKVP_IPVP(&" + StructAccces + KVPName(vprefix) + ",ObjectKvpRoot, \"" + KVPName(vprefix) + "\" ,"
                 + "&" + PExName(vprefix) + ","
                 + 0 + ","
                 + ((1<<16)-1) + ");\n"
                 + "  KVP_RegisterObject(&" + StructAccces + KVPName(vprefix) + ");\n"*/ "";
        return s;
    }

    @Override
    public String GenerateCodeDeclaration(String vprefix) {
        return "KeyValuePair " + KVPName(vprefix) + ";\n";
    }

    @Override
    public String GenerateCodeMidiHandler(String vprefix) {
        // hmm this is only one possible behavior - could also map to full MIDI range...
        int max = ((ParameterInt32HRadio) parameter).MaxValue.getInt();
        return GenerateMidiCCCodeSub(vprefix, "(data2<" + max + ")?:data2:max");
    }

    @Override
    public HRadioComponent CreateControl() {
        return new HRadioComponent(0, ((ParameterInt32HRadio) parameter).MaxValue.getInt());
    }

    @Override
    public HRadioComponent getControlComponent() {
        return (HRadioComponent) ctrl;
    }

    @Override
    public void populatePopup(JPopupMenu m) {
        super.populatePopup(m);
        JMenu m1 = new JMenu("Midi CC");
        new AssignMidiCCMenuItems(this, m1);
        m.add(m1);
    }
}
