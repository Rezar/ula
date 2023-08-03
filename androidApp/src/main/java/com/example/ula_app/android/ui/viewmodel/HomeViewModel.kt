package com.example.ula_app.android.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ula_app.android.data.DataSource
import com.example.ula_app.android.data.Goal
import com.example.ula_app.android.data.ListNode
import com.example.ula_app.android.data.MonsterCurrentStatus
import com.example.ula_app.android.repo.UserPreferencesRepository
import com.example.ula_app.android.util.DateTimeUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Instant

private const val TAG = "HomeViewModel"

class HomeViewModel() : ViewModel() {

    // Use the state flow to memorize the current movie
    // This is the current properties of the monster movie
    private val _uiState = MutableStateFlow(MonsterCurrentStatus())
    val uiState: StateFlow<MonsterCurrentStatus> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage.asSharedFlow()

    fun sendMessage(message: String) {
        viewModelScope.launch {
            _toastMessage.emit(message)
        }
    }

    // similar to setter methods in java
    fun setId(id: String) {
        _uiState.update { currentState ->
            currentState.copy(
                id = id
            )
        }
    }

    fun setTapCounter(tapCounter: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                tapCounter = tapCounter
            )
        }
    }

    fun setOpenDialog(openDialog: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                openDialog = openDialog
            )
        }
    }

    fun printGoal(goal: Int) {
        Log.i("${TAG}", "Goal is ${goal}")
    }

    // This is to store a List node of movies so that we can access the movies faster by its id as a key
    val moviesMap : MutableMap<String, ListNode> = mutableMapOf()
    init{
        create(DataSource.eggRule)
        create(DataSource.childNormalRule)
        create(DataSource.childFatRule)
        create(DataSource.childOverweightRule)
        create(DataSource.adultNormalRule)
        create(DataSource.adultFatRule)
        create(DataSource.adultFitRule)
        create(DataSource.adultOverweightRule)
    }

    val eggHead = "0_0"
    val childNormalHead = "6_1"
    val childFatHead = "11_1_1"
    val childOverweightHead = "9_1"
    val adultNormalHead = "13_1"
    val adultFatHead = "21_1"
    val adultFitHead = "24_1"
    val adultOverweightHead = "17_1_1"

    /*
    * create a linked list of movies so that we can access the movies faster O(1)
    * */
    fun create(
        moviesList: List<String>
    ): Unit {

        val dummy: ListNode? = ListNode()  // a node before the true head
        var cur: ListNode? = dummy         // this is the cursor that points to new item that is going to be added to the linked list

        // iterate to add the movie to the linked list
        for (i in moviesList) {
            cur?.next = ListNode(i)
            // create the Map with key as the movie's name, and value as the listNode
            /*
            * key      value
            * 0_0      ListNode(0_0)
            *               |
            *               v
            * 1_1      ListNode(1_1)
            *               |
            *               v
            * 1_2      ListNode(1_2)
            * */
            moviesMap.put(i, cur?.next!!)
            cur = cur?.next
        }
    }

    /*
    * This function is to update the movie after each tap according to each movies properties
    * */
    fun clickToUpdateMovies() {

        // Get current movie id. Return nothing and terminate the function when the movie is null
        val currentMonsterMovie = DataSource.monsterMovies.get(uiState.value.id)?: return


        // Check whether the movie will be locked
        if (currentMonsterMovie.hasLock) {
            sendMessage("Locked!!!")

            setOpenDialog(true)

//            return
        }

        // record this tap
        setTapCounter(uiState.value.tapCounter + 1)

        // if the tap is equal to the taps that is define in the dataSource file,
        // then reset the tap and move to the next node. If not, stay in this movie.
        if (uiState.value.tapCounter == DataSource.monsterMovies.get(uiState.value.id)?.taps) {
            setTapCounter(0)
            val nextNode = moviesMap.get(uiState.value.id)?.next?: return

            // update the id to the next movie's id. When the id is changed, all UI components that
            // related to this id will re-render. That is the exoPlayer at HomeScreen will update itself.
            setId(nextNode.value)

            Log.i("${TAG}", "${DataSource.monsterMovies.get(uiState.value.id)}")
        }
    }


    fun setAge(
        firstDateTime: Instant,
        currentStep: Int,
        goal: Int,
        maxThreshold: Double,
        minThreshold: Double
    ) {
        // If the user playing for less than 1 days, set the start movie as 0_0
        if (DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), firstDateTime)
            <= DataSource.daysToAges[0]) {
            if(uiState.value.id == eggHead){
                setId(eggHead)
            }
        }
        // If the user playing within 1 to 5 days, then the age is child
        // start setting child body status
        else if(DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), firstDateTime)
            <= DataSource.daysToAges[1] &&
            DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), firstDateTime)
            > DataSource.daysToAges[0]) {
            setChildBodyStatus(currentStep, goal, maxThreshold, minThreshold)
        }
        // If the user playing for more than 5 days, then the age is adult
        // start setting adult body status
        else if(DateTimeUtil.getDayDifference(DateTimeUtil.getCurrentDateTime(), firstDateTime)
            > DataSource.daysToAges[1]) {
            setAdultBodyStatus(currentStep, goal, maxThreshold, minThreshold)
        }
    }

    /*
    * This is to set body status when the monster is a child
    * param:
    *       currentStep: user's current step count. Linked to the sensor and will be a type of state flow
    *       goal: fitness goal from welcome page 3
    * return:
    * */
    fun setChildBodyStatus(
        currentStep: Int,
        goal: Int,
        maxThreshold: Double,
        minThreshold: Double
    ) {

        // If the cur time is out of checking range, do nothing. Otherwise check body status
        if(!checkDayTime()){
            return
        }

        if (currentStep >= DataSource.childThreshold[1] * goal * (maxThreshold + 1)) {
            if(!checkBodyStatus(uiState.value.bodyStatus, "Normal")) {
                setId(childNormalHead)
            }
        } else if (currentStep < DataSource.childThreshold[1] * goal
            && currentStep >= DataSource.childThreshold[0] * goal) {

            if(!checkBodyStatus(uiState.value.bodyStatus, "Fat")) {
                setId(childFatHead)
            }
        } else if (currentStep < DataSource.childThreshold[0] * goal * (1 - minThreshold)) {

            if(!checkBodyStatus(uiState.value.bodyStatus, "Overweight")) {
                setId(childOverweightHead)
            }
        }
    }


    /*
    * This is to set body status when the monster is an adult
    * param:
    *       currentStep: user's current step count. Linked to the sensor and will be a type of state flow
    *       goal: fitness goal from welcome page 3
    * return:
    * */
    fun setAdultBodyStatus(
        currentStep: Int,
        goal: Int,
        maxThreshold: Double,
        minThreshold: Double
    ) {

        // If the cur time is out of checking range, do nothing. Otherwise check body status
        if(!checkDayTime()){
            return
        }

        if (currentStep >= DataSource.adultThreshold[2] * goal* (maxThreshold + 1)) {

            if(!checkBodyStatus(uiState.value.bodyStatus, "Fit")) {
                setId(adultFitHead)
            }

        } else if (currentStep < DataSource.adultThreshold[2] * goal
            && currentStep >= DataSource.adultThreshold[1] * goal) {

            if(!checkBodyStatus(uiState.value.bodyStatus, "Normal")) {
                setId(adultNormalHead)
            }

        } else if (currentStep < DataSource.adultThreshold[1] * goal
            && currentStep >= DataSource.adultThreshold[0] * goal) {

            if(!checkBodyStatus(uiState.value.bodyStatus, "Fat")) {
                setId(adultFatHead)
            }

        } else if (currentStep < DataSource.adultThreshold[0] * goal* (1 - minThreshold)) {

            if(!checkBodyStatus(uiState.value.bodyStatus, "Overweight")) {
                setId(adultOverweightHead)
            }
        }
    }

    /*
    * Check if the day of time need to check the body status.
    * If we are within 00 to 12pm range, we will not check body status.
    * param:
    * return: true if need to check; false if don't need to check
    * */
    fun checkDayTime(): Boolean {

        return (DateTimeUtil.getLocalDateTime() >= DataSource.timeThreshold[0]
                && DateTimeUtil.getLocalDateTime() <= DataSource.timeThreshold[1])
    }


    /*
    * Check if the body status is the same as the current status
    * param:
    * return: true if it's the same; false if it's not the same
    * */
    fun checkBodyStatus(
        curStatus: String,
        targetStatus: String
    ): Boolean {
        return curStatus == targetStatus
    }

    /*
    * Save the steps and date to datastore
    * */


    // For Debug tab UI only
    fun setAgeAndBodyStatus(
        age: String,
        bodyStatus: String,
    ) {
        when{
            age == "Egg" && bodyStatus == "NA" -> {
                setId(eggHead)
            }
            age == "Child" && bodyStatus == "Normal" -> {
                setId(childNormalHead)
            }
            age == "Child" && bodyStatus == "Fat" -> {
                setId(childFatHead)
            }
            age == "Child" && bodyStatus == "Overweight" -> {
                setId(childOverweightHead)
            }
            age == "Adult" && bodyStatus == "Normal" -> {
                setId(adultNormalHead)
            }
            age == "Adult" && bodyStatus == "Fat" -> {
                setId(adultFatHead)
            }
            age == "Adult" && bodyStatus == "Fit" -> {
                setId(adultFitHead)
            }
            age == "Adult" && bodyStatus == "Overweight" -> {
                setId(adultOverweightHead)
            }
            else -> {}

        }
    }


/*    var id: String = "0_0"
    var tapCounter: Int = 0
    var age: String = DataSource.MonsterAgeOptions.Egg.name
    var bodyStatus: String = DataSource.MonsterStatusOptions.NA.name

    var currentMonsterMovie: MonsterMovie = MonsterMovie(
        Uri.parse(DataSource.androidResourcePath + R.raw.v0_0),
        "0_0",
        1,
        1,
        false,
        true,
        DataSource.MonsterAgeOptions.Egg.name,
        DataSource.MonsterStatusOptions.NA.name
    )*/
/*    fun updateId(id: String) {
        this.id = id
    }

    fun updateCurrentMonsterMovie(monsterMovie: MonsterMovie) {
        this.currentMonsterMovie = monsterMovie
    }*/
/*    fun switchMovie(player: ExoPlayer, newMovie: MonsterMovie) {
        player.apply {
            setMediaItem(
                MediaItem.fromUri(newMovie.uri)
            )
            prepare() // loading media and resources
        }
    }

    private val videoUris = savedStateHandle.getStateFlow("videoUris", emptyList<Uri>())

    val videoItems = videoUris.map { uri ->
        uri.map {it ->
            VideoItem(
                contentUri = it,
                mediaItem = MediaItem.fromUri(it),
                name = "No name"
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


    init {
        player.prepare()
    }

    fun addVideoUri(uri: Uri) {
        savedStateHandle["videoUris"] = videoUris.value + uri
        player.addMediaItem(MediaItem.fromUri(uri))
    }

    fun playVideo(uri: Uri) {
        player.setMediaItem(
            videoItems.value.find { it.contentUri == uri }?.mediaItem ?: return
        )
    }

    override fun onCleared() {
        super.onCleared()
        player.release()
    }*/

}