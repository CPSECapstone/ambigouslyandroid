package edu.calpoly.flipted.ui.goals

/*
import android.util.Log
import androidx.lifecycle.*
import edu.calpoly.flipted.backend.ApolloGoalsRepo
import edu.calpoly.flipted.backend.MockGoalsRepo
import edu.calpoly.flipted.businesslogic.goals.GetAllGoals
import edu.calpoly.flipted.businesslogic.goals.Goal
import edu.calpoly.flipted.businesslogic.goals.GoalCompletion
import edu.calpoly.flipted.businesslogic.goals.SaveNewCompletion
import kotlinx.coroutines.launch

class GoalsViewModel : ViewModel() {
    private val _goals : MutableLiveData<List<Goal>> = MutableLiveData()
    val goals : LiveData<List<Goal>>
        get() = _goals

    //private val repo = ApolloGoalsRepo()
    private val repo = MockGoalsRepo()
    private val getAllGoals = GetAllGoals(repo)
    private val saveCompletion = SaveNewCompletion(repo)

    fun fetchGoals() {
        viewModelScope.launch {
            _goals.value = getAllGoals.execute()
        }
    }

    fun save(completion : GoalCompletion) {
        viewModelScope.launch {
            val updatedGoal = saveCompletion.execute(completion)
            if(updatedGoal == null) {
                Log.e("MainViewModel", "Error saving updated goal!")
                //TODO: We need better error handling
                return@launch
            }
            if(_goals.value == null)
                _goals.value = getAllGoals.execute()
            val idx = _goals.value!!.indexOfFirst{it.uid == updatedGoal.uid}
            if(idx == -1) {
                Log.e("MainViewModel", "Saved completion to unknown goal!")
                _goals.value = _goals.value!! + updatedGoal
            } else {
                _goals.value = _goals.value!!.map {
                    if(it.uid == updatedGoal.uid) updatedGoal else it
                }
            }
        }
    }
}
*/
