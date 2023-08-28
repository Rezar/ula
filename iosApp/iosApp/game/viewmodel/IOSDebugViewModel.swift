//
//  IOSDebugViewModel.swift
//  iosApp
//
//  Created by Hao Wu on 8/19/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import shared
import SwiftUI

class IOSDebugViewModel: ObservableObject {
        
        private let debugViewModel: DebugViewModel
        
        @Published var debugUiState: DebugSelections = DebugSelections(
            age: "",
            bodyStatus: "",
            movieId: ""
        )
        
        private var handle: DisposableHandle?
        
        init() {
            self.debugViewModel = DebugViewModel()
        }
        
        func isEnableButton(
            ageSelection: String,
            bodyStatusSelection: String,
            movieSelection: String
        ) -> Bool {
            
            return self.debugViewModel.isEnabledButton(ageSelection: ageSelection, bodyStatusSelection: bodyStatusSelection, movieSelection: movieSelection)
        }
        
        func isOnlyChangeId(
            ageSelection: String,
            bodyStatusSelection: String,
            movieSelection: String
        ) -> Bool {
            return self.debugViewModel.isOnlyChangeId(ageSelection: ageSelection, bodyStatusSelection: bodyStatusSelection, movieSelection: movieSelection)
        }
        
        func getMovieIdOptions() -> [String] {
            return debugViewModel.getMovieIdOptions()
        }
        
//        func setId(id: String) {
//            homeViewModel.
//            print(id)
//        }
        
//        func setAgeAndBodyStatus(
//            age: String,
//            bodyStatus: String
//        ){
//            self.homeViewModel.setAgeAndBodyStatus(age: age, bodyStatus: bodyStatus)
//        }
        
        
//        func startObserving() {
//            handle = debugViewModel.uiState.subscribe(onCollect: { state in
//                if let state = state {
//                    self.debugUiState = state
//                }
//            })
//        }
//        
//        func dispose() {
//            handle?.dispose()
//        }
        
    }
    

