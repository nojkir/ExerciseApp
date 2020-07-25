package pl.nojkir.roomdatabase.ui.dialogs

import pl.nojkir.roomdatabase.data.db.entities.Exercise

interface AddDialogListener {

    fun onAddButtonClicked (exercise: Exercise)
}