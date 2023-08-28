//
//  HomeScreen.swift
//  iosApp
//
//  Created by Hao Wu on 8/16/23.
//  Copyright © 2023 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import shared
import AVKit

struct HomeScreen: View {
    
    @ObservedObject var homeViewModel: IOSHomeViewModel
    @State var openDialog: Bool = false
    
    init() {
        self.homeViewModel = IOSHomeViewModel()
        
        if homeViewModel.avPlayer == nil {
            homeViewModel.avPlayer = AVPlayer(url: Bundle.main.url(
                forResource: "v" + homeViewModel.homeUiState.id,
                withExtension: "mp4"
            )!)  // initialize the video player according to the record of the state flow
        } else {
            homeViewModel.avPlayer?.replaceCurrentItem(with: AVPlayerItem(url: Bundle.main.url(
                forResource: "v" + homeViewModel.homeUiState.id,
                withExtension: "mp4"
            )!))
        }
    }
    
    
    var body: some View {
        
        let currentMovie =  (UiMonsterMovie.Companion().allMonsterMovie[homeViewModel.homeUiState.id]?.commonMonsterMovie.monsterMovie)
        
        VStack {
            VideoPlayer(player: homeViewModel.avPlayer)
                .scaledToFit()
                .disabled(true)   // disable the controller
                .onAppear() {
                    homeViewModel.avPlayer?.play()  // play the video as default
                    addObserver(currentVideo: currentMovie)  // loop the video if the datasource record said so
                }
            
            Text(homeViewModel.homeUiState.id)
        }
        .contentShape(Rectangle()) // clickable for the whole row
        .onTapGesture {  // add clickable action here
    
            // This is to update the video file that is going to play next
            homeViewModel.clickToUpdateMovies()
            
            let currentVideoId = homeViewModel.getCurrentId()
            print("update: " + currentVideoId)

            // Play the corresponding video after the click
            homeViewModel.avPlayer?.replaceCurrentItem(with: AVPlayerItem(url: Bundle.main.url(
                forResource: "v" + currentVideoId,
                withExtension: "mp4"
            )!))
            homeViewModel.avPlayer?.play()
            
            // check if the video needs to be locked
            if currentMovie?.hasLock == true {
                homeViewModel.setOpenDialog(openDialog: true)
            }
            
            openDialog = homeViewModel.getopenDialog()
            print(openDialog)
            
        }
        
        
        .confirmationDialog("Want to play a little game? ", isPresented: $openDialog) {

            
            // TODO: add the actions to link to the games
            Button("Tic Tac Toe", action: {})
            Button("Dino Game", action: {})
            Button("Snake Game", action: {})
            Button("Flappy Bird", action: {})

            Button("Nah, I am good...", role: .cancel, action: {
                homeViewModel.setOpenDialog(openDialog: false)
                openDialog = false
            })
        }
        
        .onAppear {
            homeViewModel.startObserving()
        }
                
        .onDisappear {
            homeViewModel.dispose()
        }
        
        
    }
    
    
    
    func addObserver(
        currentVideo: MonsterMovie?
    ) {
//        let currentMovie =  UiMonsterMovie.Companion().allMonsterMovie[currentVideoId]?.commonMonsterMovie.monsterMovie
        
        if currentVideo?.loop == true {
            NotificationCenter.default.addObserver(
                forName: .AVPlayerItemDidPlayToEndTime,
                object: homeViewModel.avPlayer?.currentItem,
                queue: nil) { notif in
                            homeViewModel.avPlayer?.seek(to: .zero)
                            homeViewModel.avPlayer?.play()
                }
        }
    }
}

//struct HomeScreen_Previews: PreviewProvider {
//    static var previews: some View {
//        HomeScreen()
//    }
//}

