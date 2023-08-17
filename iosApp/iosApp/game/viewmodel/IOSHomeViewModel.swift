//
//  IOSHomeViewModel.swift
//  iosApp
//
//  Created by Hao Wu on 8/16/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import AVKit

//@available(iOS 15.0, *)
extension HomeScreen {
    @MainActor class IOSHomeViewModel: ObservableObject {
        
        private let homeViewModel: HomeViewModel
        
        @Published var avPlayer: AVPlayer? = nil
        @Published var homeUiState: MonsterCurrentStatus = MonsterCurrentStatus(
            id: "11_1_1",
            age: DataSource.MonsterAgeOptions.egg.name,
            bodyStatus: DataSource.MonsterBodyStatusOptions.na.name,
            tapCounter: 0,
            openDialog: false
        )
        
        
        private var handle: DisposableHandle?
        
        init() {
            self.homeViewModel = HomeViewModel(coroutineScope: nil)
        }
        
        func setAge(
            firstDateTime: Int64,
            currentStep: Int32,
            goal: Int32,
            maxThreshold: Double,
            minThreshold: Double
        ){
            self.homeViewModel.setAge(firstDateTime: firstDateTime, currentStep: currentStep, goal: goal, maxThreshold: maxThreshold, minThreshold: minThreshold)
        }
        
        func clickToUpdateMovies() {
            self.homeViewModel.clickToUpdateMovies()
        }
        
        func setOpenDialog(openDialog: Bool) {
            self.homeViewModel.setOpenDialog(openDialog: openDialog)
        }
        
        func setId(id: String) {
            self.homeViewModel.setId(id: id)
        }
        
        func getCurrentId() -> String {
            return self.homeViewModel.uiState.value!.id
        }
        
        func getopenDialog() -> Bool {
            return self.homeViewModel.uiState.value!.openDialog
        }
        
        func setAgeAndBodyStatus(
            age: String,
            bodyStatus: String
        ){
            self.homeViewModel.setAgeAndBodyStatus(age: age, bodyStatus: bodyStatus)
        }
        
        func startObserving() {
            handle = homeViewModel.uiState.subscribe(onCollect: { state in
                if let state = state {
                    self.homeUiState = state
                }
            })
        }
        
        func dispose() {
            handle?.dispose()
        }
        
    }
}
