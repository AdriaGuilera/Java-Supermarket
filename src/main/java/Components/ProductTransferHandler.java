package Components;
import Views.PrestatgeriesView;
import controladors.CtrlDomini;

import javax.swing.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;

public class ProductTransferHandler extends TransferHandler {
    private final int position;
    private final String prestatgeriaId;
    private final PrestatgeriesView prestatgeriesView;

    public ProductTransferHandler(PrestatgeriesView prestatgeriesView, String prestatgeriaId, int position) {
        this.prestatgeriesView = prestatgeriesView;
        this.prestatgeriaId = prestatgeriaId;
        this.position = position;
    }

    @Override
    protected Transferable createTransferable(JComponent c) {
        return new StringSelection(position + "");
    }

    @Override
    public int getSourceActions(JComponent c) {
        return MOVE;
    }

    @Override
    public boolean canImport(TransferSupport support) {
        return support.isDataFlavorSupported(DataFlavor.stringFlavor);
    }

    @Override
    public boolean importData(TransferSupport support) {
        if (!canImport(support)) return false;

        try {
            String data = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);
            int sourcePos = Integer.parseInt(data);

            JPanel targetCell = (JPanel) support.getComponent();
            int targetPos = Integer.parseInt(targetCell.getName().split("_")[1]);

            if (sourcePos == targetPos) {
                return false;
            }

            prestatgeriesView.ctrlDomini.moureProducteDeHueco(prestatgeriaId, sourcePos, targetPos);
            prestatgeriesView.refreshPrestatgeriesView();
            return true;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(prestatgeriesView,
                    "Error moving product: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}